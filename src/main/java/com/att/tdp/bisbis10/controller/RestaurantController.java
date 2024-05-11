package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.assembler.RestaurantModelAssembler;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
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
    public ResponseEntity<CollectionModel<EntityModel<Restaurant>>> getRestaurantsByCuisine
            (@RequestParam final String cuisine) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
        CollectionModel<EntityModel<Restaurant>> restaurantModels = assembler.toCollectionModel(restaurants);
        return new ResponseEntity<>(restaurantModels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> getRestaurantById(@PathVariable final Long id)
            throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        EntityModel<Restaurant> restaurantModel = assembler.toModel(restaurant);
        return new ResponseEntity<>(restaurantModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Restaurant>> addRestaurant
            (@Valid @RequestBody final Restaurant restaurant, BindingResult bindingResult) {
        validator.validate(restaurant, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        restaurantService.addRestaurant(restaurant);
        EntityModel<Restaurant> restaurantModel = assembler.toModel(restaurant);
        return new ResponseEntity<>(restaurantModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> updateRestaurant
            (@PathVariable final Long id, @Valid @RequestBody final Restaurant restaurant, BindingResult bindingResult)
            throws RestaurantNotFoundException {

        validator.validateCuisines(restaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        restaurantService.updateRestaurant(id, restaurant);

        EntityModel<Restaurant> restaurantModel = assembler.toModel(restaurantService.getRestaurantById(id));
        return new ResponseEntity<>(restaurantModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable final Long id) {

        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}