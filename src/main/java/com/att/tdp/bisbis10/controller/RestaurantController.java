package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.assembler.RestaurantModelAssembler;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.RestaurantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantValidator validator;

    @Autowired
    private RestaurantModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Restaurant>>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        CollectionModel<EntityModel<Restaurant>> restaurantModels = assembler.toCollectionModel(restaurants);
        return new ResponseEntity<>(restaurantModels, HttpStatus.OK);
    }

    @GetMapping(params = "cuisine")
    public ResponseEntity<CollectionModel<EntityModel<Restaurant>>> getRestaurantsByCuisine(@RequestParam String cuisine) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
        CollectionModel<EntityModel<Restaurant>> restaurantModels = assembler.toCollectionModel(restaurants);
        return new ResponseEntity<>(restaurantModels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        EntityModel<Restaurant> restaurantModel = assembler.toModel(restaurant);
        return new ResponseEntity<>(restaurantModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Restaurant>> addRestaurant(@Valid @RequestBody Restaurant restaurant, BindingResult bindingResult) {
        validator.validate(restaurant, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        restaurantService.addRestaurant(restaurant);
        EntityModel<Restaurant> restaurantModel = assembler.toModel(restaurant);
        return new ResponseEntity<>(restaurantModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> updateRestaurant(@PathVariable Long id, @Valid @RequestBody Restaurant restaurant, BindingResult bindingResult) {
        validator.validateCuisines(restaurant, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        if (restaurantService.getRestaurantById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        restaurantService.updateRestaurant(id, restaurant);
        Restaurant updatedRestaurant = restaurantService.getRestaurantById(id); // Assuming updateRestaurant returns the updated restaurant

        EntityModel<Restaurant> restaurantModel = assembler.toModel(updatedRestaurant);
        return new ResponseEntity<>(restaurantModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        Restaurant existingRestaurant = restaurantService.getRestaurantById(id);
        if (existingRestaurant == null) {
            return ResponseEntity.notFound().build();
        }

        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}

