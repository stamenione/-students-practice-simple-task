package com.example.MealOrder.service;

import com.example.MealOrder.exception.*;
import com.example.MealOrder.model.*;
import com.example.MealOrder.model.dto.*;
import com.example.MealOrder.model.mapper.Mapper;
import com.example.MealOrder.repository.*;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CustomRestaurantServiceImpl implements CustomRestaurantService {

    @Autowired
    private CustomOrderRepository customOrderRepository;

    @Autowired
    private RestaurantMealRepository restaurantMealRepository;

    @Autowired
    private CustomRestaurantRepository customRestaurantRepository;

    @Autowired
    private CustomMealRepository customMealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private EmailServiceImpl emailServiceImpl;


    @Override
    public CustomRestaurantOrderDTO orderFromCustomRestaurant(CustomRestaurantOrderDTO customRestaurantOrderDTO) {
        UserEntity user = userRepository.findByEmail(customRestaurantOrderDTO.getEmail());

        if (user == null) {
            throw new UserNotFoundException(new ErrorResource("User does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        CustomMealEntity customMeal = customMealRepository.findByNameAndPrice(customRestaurantOrderDTO.getMealName(), customRestaurantOrderDTO.getMealPrice());
        if (customMeal == null) {
            throw new CustomMealNotFoundException(new ErrorResource("Custom meal does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }
        CustomRestaurantEntity customRestaurant = customRestaurantRepository.findByName(customRestaurantOrderDTO.getRestaurantName());
        if (customRestaurant == null) {
            throw new CustomRestaurantNotFoundException(new ErrorResource("Custom restaurant does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        RestaurantMealEntity restaurantMeal = restaurantMealRepository.findByRestaurantAndMeal(customRestaurant.getId(), customMeal.getId());
        if (restaurantMeal == null) {
            throw new RestaurantMealNotFoundException(new ErrorResource("Restaurant meal does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        CustomOrderEntity customOrder = new CustomOrderEntity();
        customOrder.setRestaurantMealEntity(restaurantMeal);
        customOrder.setUserEntity(user);
        customOrder.setQuantity(customRestaurantOrderDTO.getQuantity());

        customOrderRepository.save(customOrder);

        return customRestaurantOrderDTO;
    }


    @Override
    public CustomRestaurantWithOrderDTO addCustomRestaurant(String token, CustomRestaurantWithOrderDTO customRestaurantWithOrderDTO) {


        if (customRestaurantWithOrderDTO.getOrderTime().isBefore(LocalTime.now())) {
            throw new InvalidTimeException(new ErrorResource("Time must be after current time", HttpStatus.CONFLICT, LocalDate.now()));
        }


        for (CustomMealDTO meal : customRestaurantWithOrderDTO.getMeals()) {
            if (meal.getPrice() <= 0 || meal.getQuantity() <= 0) {
                throw new PriceLessThanOrEqualToZeroException(new ErrorResource("Price must be positive", HttpStatus.BAD_REQUEST, LocalDate.now()));
            }
        }


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


        CustomRestaurantEntity restaurant = customRestaurantRepository.findByName(customRestaurantWithOrderDTO.getRestaurantName());

        if (restaurant != null) {
            throw new RestaurantAlreadyExistsException(new ErrorResource("Restaurant " + customRestaurantWithOrderDTO.getRestaurantName() + " already exists", HttpStatus.CONFLICT, LocalDate.now()));
        }

        if (customRestaurantWithOrderDTO.getMeals().isEmpty()) {
            throw new RestaurantIsEmptyException(new ErrorResource("Restaurant must have at least 1 meal on menu", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        restaurant = customRestaurantRepository.save(new CustomRestaurantEntity(customRestaurantWithOrderDTO.getRestaurantName(), customRestaurantWithOrderDTO.getOrderTime(), user));


        for (CustomMealDTO meal : customRestaurantWithOrderDTO.getMeals()) {

            CustomMealEntity customMealEntity = customMealRepository.findByNameAndPrice(meal.getName(), meal.getPrice());
            if (customMealEntity == null) {
                customMealEntity = customMealRepository.save(new CustomMealEntity(meal.getName(), meal.getPrice()));
            }

            RestaurantMealEntity restaurantMealEntity = restaurantMealRepository.findByCustomMealEntityAndCustomRestaurantEntity(customMealEntity, restaurant);
            if (restaurantMealEntity == null) {
                restaurantMealEntity = restaurantMealRepository.save(new RestaurantMealEntity(customMealEntity, restaurant));
            }


            if (meal.getWillOrder() == 1) {
                customOrderRepository.save(new CustomOrderEntity(meal.getQuantity(), restaurantMealEntity, user));
            }


        }

        List<UserEntity> users = userRepository.findAll();


        for (UserEntity u : users) {
            if (!u.getEmail().equals(email)) {
                emailServiceImpl.sendEmail(u.getEmail(), "Somebody will order from other restaurant",
                        "Dear " + u.getFirstName() + ",<br/>You can also order from " + restaurant.getName() + " until " + restaurant.getOrderTime() + ".");
            }
        }


        customOrdersMail(restaurant);


        return customRestaurantWithOrderDTO;
    }


    @Override
    public List<String> getAllRestaurants() {
        List<CustomRestaurantEntity> customRestaurants = customRestaurantRepository.findAll();

        if (customRestaurants == null || customRestaurants.isEmpty()) {
            throw new RestaurantNotFoundException(new ErrorResource("Not a single restaurant is found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        return customRestaurants.stream().map(r -> r.getName()).collect(Collectors.toList());
    }


    @Override
    public List<CustomMealOfRestaurantDTO> getRestaurantMenu(String name) {

        CustomRestaurantEntity restaurant = customRestaurantRepository.findByName(name);

        if (restaurant == null) {
            throw new RestaurantNotFoundException(new ErrorResource("Restaurant " + name + " not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        List<RestaurantMealEntity> restaurantMeals = restaurantMealRepository.findAllByCustomRestaurantEntity(restaurant);


        if (restaurantMeals == null || restaurantMeals.isEmpty()) {
            throw new RestaurantMealNotFoundException(new ErrorResource("Restaurant " + name + " is empty", HttpStatus.NOT_FOUND, LocalDate.now()));
        }


        List<CustomMealOfRestaurantDTO> meals = new ArrayList<>();


        for (RestaurantMealEntity restaurantMeal : restaurantMeals) {
            meals.add(mapper.newCustomMealOfRestaurantDTO(restaurantMeal.getCustomMealEntity()));
        }

        return meals;
    }


    private void customOrdersMail(CustomRestaurantEntity restaurant) {
        long delay = LocalTime.now().until(restaurant.getOrderTime(), ChronoUnit.SECONDS);


        Runnable task = () -> sendCustomOrders(restaurant);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        executor.schedule(task, delay, TimeUnit.SECONDS);
    }


    private void sendCustomOrders(CustomRestaurantEntity restaurant) {
        List<SumCustomOrderDTO> orders = customOrderRepository.findCustomOrdersForRestaurant(restaurant.getName());


        int sum = 0;
        String text = "Dear " + restaurant.getUserEntity().getFirstName() + ",<br/>Here are orders for the " + restaurant.getName() +
                ":";
        for (SumCustomOrderDTO s : orders) {
            text += "<br/>" + s.getFirstName() + " " + s.getLastName() + " has ordered " + s.getQuantity() + " " + s.getName() + ", which costs " + s.getPrice() * s.getQuantity() + " din.";
            sum += s.getPrice() * s.getQuantity();
        }
        text += "<br/>Total is: " + sum + " din.";

        emailServiceImpl.sendEmail(restaurant.getUserEntity().getEmail(), "Orders for " + restaurant.getName(), text);


        deleteRestaurant(restaurant);


    }


    private void deleteRestaurant(CustomRestaurantEntity restaurant) {
        List<RestaurantMealEntity> restaurantMealEntities = restaurantMealRepository.findAllByCustomRestaurantEntity(restaurant);

        for (RestaurantMealEntity r : restaurantMealEntities) {
            List<CustomOrderEntity> customOrderEntities = customOrderRepository.findAllByRestaurantMealEntity(r);

            for (CustomOrderEntity c : customOrderEntities) {
                customOrderRepository.delete(c);
            }

            restaurantMealRepository.delete(r);
        }

        customRestaurantRepository.delete(restaurant);
    }


}
