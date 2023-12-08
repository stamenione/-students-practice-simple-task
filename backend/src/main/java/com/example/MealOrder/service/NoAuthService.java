package com.example.MealOrder.service;

import com.example.MealOrder.model.dto.MenuDTO;
import com.example.MealOrder.model.dto.UserDTO;

public interface NoAuthService {

    UserDTO register(UserDTO userDTO);

    MenuDTO menu();

}
