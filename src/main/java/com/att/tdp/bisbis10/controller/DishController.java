package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.service.DishService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.DishValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants/{id}/dishes")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    public RestaurantService restaurantService;
    @Autowired
    private DishValidator validator;

    @PostMapping
    public ResponseEntity<String> addDish(@PathVariable("id") Long restaurantId, @Valid @RequestBody Dish dish, BindingResult bindingResult) {
        validator.validate(dish, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        if (restaurantService.getRestaurantById(restaurantId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
        dishService.addDish(restaurantId, dish);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<String> updateDish(@PathVariable("id") Long restaurantId, @PathVariable Long dishId,
                                             @Valid @RequestBody Dish dish, BindingResult bindingResult) {
        validator.validateUpdate(dish, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        if (restaurantService.getRestaurantById(restaurantId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
        if (dishRepository.findById(dishId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dish not found");
        }
        dishService.updateDish(dishId, dish);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<String> deleteDish(@PathVariable("id") Long restaurantId, @PathVariable Long dishId) {
        if (restaurantService.getRestaurantById(restaurantId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
        if (dishRepository.findById(dishId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dish not found");
        }
        dishService.deleteDish(restaurantId, dishId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getDishesByRestaurant(@PathVariable("id") Long restaurantId) {
        List<Dish> dishes = dishService.getDishesByRestaurant(restaurantId);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
}
