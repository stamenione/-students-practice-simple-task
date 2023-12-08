package com.example.MealOrder.controller;

import com.example.MealOrder.model.dto.CustomRestaurantOrderDTO;
import com.example.MealOrder.model.dto.CustomRestaurantWithOrderDTO;
import com.example.MealOrder.service.CustomRestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/restaurant")
@CrossOrigin("http://localhost:3000")
public class CustomRestaurantController {


    @Autowired
    private CustomRestaurantServiceImpl customRestaurantServiceImpl;


    @PostMapping("/order")
    public ResponseEntity<?> orderFromCustomRestaurant(@RequestBody CustomRestaurantOrderDTO customRestaurantOrderDTO) {
        return new ResponseEntity<>(customRestaurantServiceImpl.orderFromCustomRestaurant(customRestaurantOrderDTO), HttpStatus.OK);
    }


    @PostMapping("/addRestaurant")
    public ResponseEntity<?> addCustomRestaurant(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid CustomRestaurantWithOrderDTO customRestaurantWithOrderDTO) {
        return new ResponseEntity<>(customRestaurantServiceImpl.addCustomRestaurant(token.split(" ")[1], customRestaurantWithOrderDTO), HttpStatus.OK);
    }


    @GetMapping("/allRestaurants")
    public ResponseEntity<?> getAllRestaurants() {
        return new ResponseEntity<>(customRestaurantServiceImpl.getAllRestaurants(), HttpStatus.OK);
    }


    @GetMapping("/restaurantMenu")
    public ResponseEntity<?> getRestaurantMenu(@RequestParam String name) {
        return new ResponseEntity<>(customRestaurantServiceImpl.getRestaurantMenu(name), HttpStatus.OK);
    }

}
