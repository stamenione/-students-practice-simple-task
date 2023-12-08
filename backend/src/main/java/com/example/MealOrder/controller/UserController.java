package com.example.MealOrder.controller;

import com.example.MealOrder.model.dto.ChangePasswordDTO;
import com.example.MealOrder.model.dto.OrderDTO;
import com.example.MealOrder.model.dto.UserDTO;
import com.example.MealOrder.model.dto.UserLoginDTO;
import com.example.MealOrder.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin("http://localhost:3000")
public class UserController {


    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userServiceImpl.login(userLoginDTO), HttpStatus.OK);
    }


    @GetMapping("/currentMeals")
    public ResponseEntity<?> currentMeals() {
        return new ResponseEntity<>(userServiceImpl.currentMeals(), HttpStatus.OK);
    }


    @PostMapping("/orderMeal")
    public ResponseEntity<?> orderMeal(@RequestBody List<OrderDTO> orderDTO) {
        return new ResponseEntity<>(userServiceImpl.orderMeal(orderDTO), HttpStatus.OK);
    }


    @GetMapping("/todayUsers")
    public ResponseEntity<?> todayUsers() {
        return new ResponseEntity<>(userServiceImpl.todayUsers(), HttpStatus.OK);
    }


    @PostMapping("/markPayed")
    public ResponseEntity<?> markPayed(@RequestParam String email) {
        return new ResponseEntity<>(userServiceImpl.markPayed(email), HttpStatus.OK);
    }


    @GetMapping("/userDetails")
    public ResponseEntity<?> userDetail(@RequestHeader(value = "Authorization") String token) {
        return new ResponseEntity<>(userServiceImpl.userDetails(token.split(" ")[1]), HttpStatus.OK);
    }


    @PutMapping("/updateUserDetails")
    public ResponseEntity<?> updateUserDetails(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userServiceImpl.updateUserDetails(userDTO), HttpStatus.OK);
    }


    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return new ResponseEntity<>(userServiceImpl.changePassword(changePasswordDTO), HttpStatus.OK);
    }

}
