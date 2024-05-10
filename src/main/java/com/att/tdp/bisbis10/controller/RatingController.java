package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.entity.Rating;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.service.RatingService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.RatingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RatingValidator validator;

    @PostMapping("/ratings")
    public ResponseEntity<String> addRating(@Valid @RequestBody Rating ratingData, BindingResult bindingResult) {

        validator.validate(ratingData, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
        }
        Restaurant restaurant = restaurantService.getRestaurantById(ratingData.getRestaurantId());
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
        ratingService.addRating(ratingData, restaurant);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

