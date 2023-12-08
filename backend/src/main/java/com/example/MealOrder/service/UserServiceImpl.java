package com.example.MealOrder.service;

import com.example.MealOrder.enums.RoleEnum;
import com.example.MealOrder.exception.*;
import com.example.MealOrder.model.*;
import com.example.MealOrder.model.dto.*;
import com.example.MealOrder.model.mapper.Mapper;
import com.example.MealOrder.repository.*;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuMealRepository menuMealRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;
    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private Mapper mapper;

    @Autowired
    private TelegramServiceImpl telegramServiceImpl;


    public String login(UserLoginDTO userLoginDTO) {
        UserEntity user = userRepository.findByEmail(userLoginDTO.getEmail());

        if (user != null && user.getRoleEntity().getName().equals(RoleEnum.ROLE_ADMIN.toString())) {
            if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                throw new WrongPasswordException(new ErrorResource("Wrong password", HttpStatus.BAD_REQUEST, LocalDate.now()));
            }
            return user.getEmail();
        } else {
            if (user == null) {

                throw new UserNotFoundException(new ErrorResource("User " + userLoginDTO.getEmail() + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
            }

            if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                throw new WrongPasswordException(new ErrorResource("Wrong password", HttpStatus.BAD_REQUEST, LocalDate.now()));
            }

            return user.getEmail();
        }
    }


    public MenuDTO currentMeals() {

        List<MenuEntity> menus = menuRepository.findAll();
        LocalDate today = LocalDate.now();

        for (MenuEntity menu : menus) {

            if (today.isAfter(menu.getStartDate().minusDays(1)) && today.isBefore(menu.getStartDate().plusDays(7))) {

                List<MenuMealEntity> menuMeals = menuMealRepository.findByMenuEntity(menu);

                List<MealDTO> mealsDTO = new ArrayList<>();
                for (MenuMealEntity menuMeal : menuMeals) {

                    MealEntity meal = mealRepository.findById(menuMeal.getMealEntity().getId()).get();

                    if (meal.getDayBefore() == 1 && menuMeal.getMealDate().isEqual(today.plusDays(1)) && LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.parse("15:30:00"))) && LocalDateTime.now().isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.parse("15:05:00")))) {
                        mealsDTO.add(new MealDTO(meal.getName(), meal.getPrice(), meal.getDayBefore(), menuMeal.getMealDate(), meal.getMealTypeEntity().getType()));
                    } else if (meal.getDayBefore() == 0 && menuMeal.getMealDate().isEqual(today) && LocalDateTime.now().isBefore(LocalDateTime.of(LocalDate.now(), LocalTime.parse("15:10:00"))) && LocalDateTime.now().isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.parse("15:05:00")))) {
                        mealsDTO.add(new MealDTO(meal.getName(), meal.getPrice(), meal.getDayBefore(), menuMeal.getMealDate(), meal.getMealTypeEntity().getType()));
                    }

                }

                return mapper.newMenuDTO(menu, mealsDTO);

            }
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        LocalDate lastMonday = LocalDateTime.ofInstant(c.getTime().toInstant(), c.getTimeZone().toZoneId()).toLocalDate();

        throw new MenuNotFoundException(new ErrorResource("Menu starting on " + lastMonday + " is not found",
                HttpStatus.NOT_FOUND, LocalDate.now()));
    }


    public List<OrderDTO> orderMeal(List<OrderDTO> orderDTO) {
        if (orderDTO.size() == 0 || orderDTO == null) {
            throw new UserOrderEmptyException(new ErrorResource("You cannot make empty order.", HttpStatus.BAD_REQUEST, LocalDate.now()));
        } else {
            for (OrderDTO order : orderDTO) {
                UserEntity userEntity = userRepository.findByEmail(order.getEmail());

                if (userEntity == null) {
                    throw new UserNotFoundException(new ErrorResource("User " + order.getEmail() + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
                }

                MealEntity meal = mealRepository.findByNameAndPrice(order.getName(), order.getPrice());

                if (meal == null) {
                    throw new MealNotFoundException(new ErrorResource("Meal " + order.getName() + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
                }

                MenuEntity menu = menuRepository.findByStartDate(order.getMenuStartingDate());

                if (menu == null) {

                    throw new MenuNotFoundException(new ErrorResource("Menu starting on " + order.getMenuStartingDate() + " is not found",
                            HttpStatus.NOT_FOUND, LocalDate.now()));
                }

                MenuMealEntity menuMeal = menuMealRepository.findByMenuEntityAndMealEntityAndMealDate(menu, meal, order.getMealDate());

                if (menuMeal == null) {
                    throw new MealInMenuNotFoundException(new ErrorResource("Meal " + meal.getName() + " is not found in menu starting on " + menu.getStartDate(), HttpStatus.NOT_FOUND, LocalDate.now()));
                }

                MenuDTO availableMenu = currentMeals();
                if (order.getMenuStartingDate().equals(availableMenu.getStartDate())) {

                    availableMenu.getMeals().stream().forEach(mDTO -> {
                        if (order.getName().equals(mDTO.getName()) && order.getPrice() == mDTO.getPrice() && order.getMealDate().equals(mDTO.getMealDate()) && order.getMealType().equals(mDTO.getMealType())) {
                            UserOrderEntity userOrder = new UserOrderEntity(order.getQuantity(), menuMeal, userEntity);
                            userOrderRepository.save(userOrder);
                        }
                    });
                }
            }
        }

        return orderDTO;
    }


    public List<UserOrderDTO> todayUsers() {
        List<UserOrderEntity> orders = userOrderRepository.findAll();

        List<UserOrderDTO> users = new ArrayList<>();
        upperLoop:
        for (UserOrderEntity order : orders) {
            if (order.getMenuMealEntity().getMealDate().equals(LocalDate.now())) {

                int needToPay = order.getQuantity() * order.getMenuMealEntity().getMealEntity().getPrice();

                for (UserOrderDTO u : users) {
                    if (u.getEmail().equals(order.getUserEntity().getEmail())) {
                        u.setNeedToPay(u.getNeedToPay() + needToPay);
                        continue upperLoop;
                    }
                }

                UserOrderDTO user = mapper.newUserOrderDTO(order.getUserEntity(), order.getUserEntity().getAddressEntity(), needToPay, order.getPayed());
                users.add(user);

            }
        }

        return users;
    }


    public boolean markPayed(String email) {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(new ErrorResource("User " + email + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        List<UserOrderEntity> orders = userOrderRepository.findByUserEntity(user);
        for (UserOrderEntity order : orders) {
            if (order.getMenuMealEntity().getMealDate().isEqual(LocalDate.now())) {
                if (order.getPayed() == 0) {
                    order.setPayed((byte) 1);
                } else {
                    order.setPayed((byte) 0);
                }

                userOrderRepository.save(order);
            }
        }

        return true;
    }


    public UserDetailsDTO userDetails(String token) {

        SignedJWT decodedJWT;
        try {
            decodedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new UserNotFoundException(new ErrorResource("User " + "-" + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }
        String email = decodedJWT.getPayload().toJSONObject().get("sub").toString();


        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(new ErrorResource("User " + email + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        return mapper.newUserDetailsDTO(user, user.getAddressEntity());
    }

    @Scheduled(cron = "0 10 15 ? * MON-FRI", zone = "CET")
    public void theChoosenOne() {

        List<UserEntity> userOrderEntityList = userOrderRepository.findByUserWitDate(LocalDate.now());

        List<UserEntity> userEntitiesNoDuplicates = new ArrayList<>(new HashSet<>(userOrderEntityList));

        UserEntity user = userRepository.findByChoosenOne((byte) 1);
        if (user != null) {
            throw new AlreadyTheChoosenOneException(
                    new ErrorResource("The user is already the choosen one. The Choosen one status: " + user.getTheChoosenOne()
                            , HttpStatus.CONFLICT, LocalDate.now()));
        }

        Random rand = new Random();
        if (userEntitiesNoDuplicates.size() > 0) {
            UserEntity theChoosenOne = userEntitiesNoDuplicates.get(Math.abs(rand.nextInt(userEntitiesNoDuplicates.size())));

            theChoosenOne.setTheChoosenOne((byte) 1);
            userRepository.save(theChoosenOne);

            emailServiceImpl.sendEmail(theChoosenOne.getEmail(), "You have been chosen!",
                    "Dear " + theChoosenOne.getFirstName() + ",<br/>You are The Chosen One today");


        }

        //return "The Choosen one has be choosen. :D";
    }

    public UserDTO updateUserDetails(UserDTO userDTO) {

        UserEntity user = userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
            throw new UserNotFoundException(new ErrorResource("User " + userDTO.getEmail() + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new WrongPasswordException(new ErrorResource("Wrong password", HttpStatus.BAD_REQUEST, LocalDate.now()));
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setNumber(userDTO.getNumber());

        AddressEntity address = addressRepository.findByStreetNameAndStreetNumber(userDTO.getStreetName(), userDTO.getStreetNumber());
        if (address != null) {
            user.setAddressEntity(address);
        } else {
            AddressEntity addressEntity = new AddressEntity(userDTO.getStreetName(), userDTO.getStreetNumber());
            AddressEntity addressSaved = addressRepository.save(addressEntity);
            user.setAddressEntity(addressSaved);
        }


        userRepository.save(user);
        return userDTO;
    }


    @Scheduled(cron = "0 30 15 ? * MON-FRI", zone = "CET")
    public void resetTheChoosenOne() {
        UserEntity theChoosenOne = userRepository.findByChoosenOne((byte) 1);

        if (theChoosenOne == null) {
            throw new TheChoosenOneDoesNotExistException(new ErrorResource("The Choosen One does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        theChoosenOne.setTheChoosenOne((byte) 0);
        userRepository.save(theChoosenOne);


        emailServiceImpl.sendEmail(theChoosenOne.getEmail(), "End of working day!",
                "Dear " + theChoosenOne.getFirstName() + ",<br/>You are no longer chosen one.");
    }


    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {

        UserEntity user = userRepository.findByEmail(changePasswordDTO.getEmail());

        if (user == null) {

            throw new UserNotFoundException(new ErrorResource("User " + changePasswordDTO.getEmail() + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        if (!user.getPassword().equals(changePasswordDTO.getOldPassword())) {
            throw new WrongPasswordException(new ErrorResource("Wrong password", HttpStatus.BAD_REQUEST, LocalDate.now()));
        }

        user.setPassword(changePasswordDTO.getNewPassword());

        userRepository.save(user);

        return true;
    }

    @Scheduled(cron = "0 15,18 15 ? * MON-FRI", zone = "CET")
    public void mailReminderToPay() {
        List<UserOrderDTO> users = todayUsers();

        for (UserOrderDTO user : users) {
            if (user.getPayed() == 0) {
                emailServiceImpl.sendEmail(user.getEmail(), "You need to pay!",
                        "Dear " + user.getFirstName() + ",<br/>You need to pay " + user.getNeedToPay() + "din for todays order.");

            }
        }

    }

    @Scheduled(cron = "0 5 15 ? * MON-FRI", zone = "CET")
    public void mailSendCurrentMeals() {
        MenuDTO currentMeals = currentMeals();

        List<UserEntity> users = userRepository.findAll();

        StringBuilder stringCurrentMeals = new StringBuilder();
        for (MealDTO meal : currentMeals.getMeals()) {
            stringCurrentMeals.append("Meal name: " + meal.getName() + "<br/>" +
                    "Meal price: " + meal.getPrice() + "<br/>");
        }

        for (UserEntity user : users) {
            emailServiceImpl.sendEmail(user.getEmail(), "You can now order meals"
                    , "Dear " + user.getFirstName() + ",<br/><br/>"
                            + "Here is our menu for today. Let your stomach chose for you :) <br/>"
                            + stringCurrentMeals + "<br/>"
                            + "Here is the link where u can order your meal "
                            + "http://localhost:3000/"
            );
        }
    }

    @Scheduled(cron = "0 10 15 ? * MON-FRI", zone = "CET")
    public void mailSendOrderToUser() {

        if (telegramMessage()) {
            List<UserOrderEntity> orders = userOrderRepository.findByUserMealsForToday(LocalDate.now());

            if (orders != null) {
                Map<String, List<Pair<MealEntity, Integer>>> map = new HashMap<>();

                orders.forEach(order -> {
                    if (map.containsKey(order.getUserEntity().getEmail())) {
                        map.get(order.getUserEntity().getEmail())
                                .add(Pair.of(order.getMenuMealEntity().getMealEntity(), order.getQuantity()));
                    } else {
                        List<Pair<MealEntity, Integer>> meals = new ArrayList<>();
                        meals.add(Pair.of(order.getMenuMealEntity().getMealEntity(), order.getQuantity()));
                        map.put(order.getUserEntity().getEmail(), meals);
                    }
                });

                map.forEach((String email, List<Pair<MealEntity, Integer>> meals) -> {
                    StringBuilder builder = new StringBuilder();
                    for (Pair<MealEntity, Integer> meal : meals) {
                        builder.append("Meal name: " + meal.getFirst().getName() + "<br/>")
                                .append("Meal price: " + meal.getFirst().getPrice() + "<br/>")
                                .append("Meal quantity: " + meal.getSecond() + "<br/>");
                    }

                    emailServiceImpl.sendEmail(email, "Your orders for today", "Here is your order for today. <br/>" +
                            builder);
                });
            } else {
                throw new OrdersNotFoundException(new ErrorResource("There are no orders for today.", HttpStatus.NOT_FOUND, LocalDate.now()));
            }
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException(new ErrorResource("User " + email + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getRoleEntity().getName()));
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }


    public boolean telegramMessage() {
        List<SumOrderDTO> orders = userOrderRepository.findOrdersForDate(LocalDate.now());

        if (orders != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");

            String text = "Simple Task order for " + orders.get(0).getMealDate().format(formatter) + ":%0A";
            for (SumOrderDTO order : orders) {
                text += order.getQuantity() + " " + order.getName() + "%0A";
            }

            telegramServiceImpl.sendMessage(text);

            return true;
        }
        return false;
    }
}
