package com.example.MealOrder.controller;

import com.example.MealOrder.model.dto.AddMealDTO;
import com.example.MealOrder.model.dto.AddMenuDTO;
import com.example.MealOrder.model.dto.AddMenuMealDTO;
import com.example.MealOrder.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @PostMapping("/addMeal")
    public ResponseEntity<?> addMeal(@RequestBody AddMealDTO addMealDTO) {
        return new ResponseEntity<>(adminServiceImpl.addMeal(addMealDTO), HttpStatus.OK);
    }

    @PostMapping("/addMenu")
    public ResponseEntity<?> addMenu(@RequestBody AddMenuDTO addMenuDTO) {
        return new ResponseEntity<>(adminServiceImpl.addMenu(addMenuDTO), HttpStatus.OK);
    }

    @PostMapping("/addMealToMenu")
    public ResponseEntity<?> addMealToMenu(@RequestBody AddMenuMealDTO addMenuMealDTO) {
        return new ResponseEntity<>(adminServiceImpl.addMenuMeal(addMenuMealDTO), HttpStatus.OK);
    }

    @GetMapping("/allUsedDates")
    public ResponseEntity<?> allAvailableDates() {
        return new ResponseEntity<>(adminServiceImpl.allUsedDatesForMenus(), HttpStatus.OK);
    }

    @GetMapping("/allMealTypes")
    public ResponseEntity<?> allMealTypes() {
        return new ResponseEntity<>(adminServiceImpl.allMealTypes(), HttpStatus.OK);
    }

    @GetMapping("/nextMenu")
    public ResponseEntity<?> nextMenus() {
        return new ResponseEntity<>(adminServiceImpl.nextMenus(), HttpStatus.OK);
    }

    @GetMapping("/allMeals")
    public ResponseEntity<?> allMeals() {
        return new ResponseEntity<>(adminServiceImpl.allMeals(), HttpStatus.OK);
    }
}
