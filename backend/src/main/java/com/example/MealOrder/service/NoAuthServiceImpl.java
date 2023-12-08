package com.example.MealOrder.service;

import com.example.MealOrder.enums.RoleEnum;
import com.example.MealOrder.exception.EmailAlreadyExistsException;
import com.example.MealOrder.exception.ErrorResource;
import com.example.MealOrder.exception.MenuNotFoundException;
import com.example.MealOrder.model.*;
import com.example.MealOrder.model.dto.MealDTO;
import com.example.MealOrder.model.dto.MenuDTO;
import com.example.MealOrder.model.dto.UserDTO;
import com.example.MealOrder.model.mapper.Mapper;
import com.example.MealOrder.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NoAuthServiceImpl implements NoAuthService {


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


    public UserDTO register(UserDTO userDTO) {

        AddressEntity addressEntity = new AddressEntity(userDTO.getStreetName(), userDTO.getStreetNumber());
        RoleEntity roleEntity;
        if (userDTO.getEmail().startsWith("admin")) {
            roleEntity = roleRepository.findByName(RoleEnum.ROLE_ADMIN.toString());
        } else {
            roleEntity = roleRepository.findByName(RoleEnum.ROLE_USER.toString());
        }
        UserEntity userEntity;
        AddressEntity address = addressRepository.findByStreetNameAndStreetNumber(addressEntity.getStreetName(), addressEntity.getStreetNumber());
        if (address != null) {
            userEntity = new UserEntity(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getNumber(), address, roleEntity);
        } else {
            addressRepository.save(addressEntity);
            userEntity = new UserEntity(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getNumber(), addressEntity, roleEntity);
        }

        if (userRepository.findByEmail(userEntity.getEmail()) != null) {
            throw new EmailAlreadyExistsException(new ErrorResource("Email " + userEntity.getEmail() + " already exists", HttpStatus.CONFLICT, LocalDate.now()));
        }

        UserEntity user = userRepository.save(userEntity);

        return mapper.newUserDTO(user, addressEntity);
    }


    public MenuDTO menu() {

        List<MenuEntity> menus = menuRepository.findAll();
        LocalDate today = LocalDate.now();

        for (MenuEntity menu : menus) {

            if (today.isAfter(menu.getStartDate().minusDays(1)) && today.isBefore(menu.getStartDate().plusDays(7))) {

                List<MenuMealEntity> menuMeals = menuMealRepository.findByMenuEntity(menu);

                List<MealDTO> mealsDTO = new ArrayList<>();
                for (MenuMealEntity menuMeal : menuMeals) {

                    MealEntity meal = mealRepository.findById(menuMeal.getMealEntity().getId()).get();
                    mealsDTO.add(new MealDTO(meal.getName(), meal.getPrice(), meal.getDayBefore(), menuMeal.getMealDate(), meal.getMealTypeEntity().getType()));
                }

                Collections.sort(mealsDTO, Comparator.comparing(MealDTO::getMealDate));

                return mapper.newMenuDTO(menu, mealsDTO);

            }
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        LocalDate lastMonday = LocalDateTime.ofInstant(c.getTime().toInstant(), c.getTimeZone().toZoneId()).toLocalDate();

        throw new MenuNotFoundException(new ErrorResource("Menu starting on " + lastMonday + " is not found",
                HttpStatus.NOT_FOUND, LocalDate.now()));
    }
}
