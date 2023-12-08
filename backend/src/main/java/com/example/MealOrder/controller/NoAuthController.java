package com.example.MealOrder.controller;

import com.example.MealOrder.model.dto.UserDTO;
import com.example.MealOrder.service.NoAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("http://localhost:3000")
public class NoAuthController {


    @Autowired
    private NoAuthServiceImpl noAuthServiceImpl;

    @GetMapping("/menu")
    public ResponseEntity<?> menu() {
        return new ResponseEntity<>(noAuthServiceImpl.menu(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(noAuthServiceImpl.register(userDTO), HttpStatus.OK);
    }

}
