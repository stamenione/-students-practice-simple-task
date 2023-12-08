package com.example.MealOrder.service;

import com.example.MealOrder.model.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    String login(UserLoginDTO userLoginDTO);

    MenuDTO currentMeals();

    List<OrderDTO> orderMeal(List<OrderDTO> orderDTO);

    List<UserOrderDTO> todayUsers();

    boolean markPayed(String email);

    UserDetailsDTO userDetails(String token);

    void theChoosenOne();

    UserDTO updateUserDetails(UserDTO userDTO);

    void resetTheChoosenOne();

    boolean changePassword(ChangePasswordDTO changePasswordDTO);

    void mailReminderToPay();

    void mailSendCurrentMeals();

    void mailSendOrderToUser();

    UserDetails loadUserByUsername(String email);

    boolean telegramMessage();
}
