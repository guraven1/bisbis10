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

/**
 * Controller class for handling restaurant-related operations.
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantValidator validator;

    @Autowired
    private RestaurantModelAssembler assembler;

    /**
     * Retrieves all restaurants.
     *
     * @return ResponseEntity containing a collection of restaurant entities
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Restaurant>>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        CollectionModel<EntityModel<Restaurant>> restaurantModels = assembler.toCollectionModel(restaurants);
        return new ResponseEntity<>(restaurantModels, HttpStatus.OK);
    }

    /**
     * Retrieves restaurants by cuisine.
     *
     * @param cuisine the cuisine to filter by
     * @return ResponseEntity containing a collection of restaurant entities
     */
    @GetMapping(params = "cuisine")
    public ResponseEntity<CollectionModel<EntityModel<Restaurant>>> getRestaurantsByCuisine
            (@RequestParam final String cuisine) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
        CollectionModel<EntityModel<Restaurant>> restaurantModels = assembler.toCollectionModel(restaurants);
        return new ResponseEntity<>(restaurantModels, HttpStatus.OK);
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id the ID of the restaurant to retrieve
     * @return ResponseEntity containing the restaurant entity
     * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Restaurant>> getRestaurantById(@PathVariable final Long id)
            throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        EntityModel<Restaurant> restaurantModel = assembler.toModel(restaurant);
        return new ResponseEntity<>(restaurantModel, HttpStatus.OK);
    }

    /**
     * Adds a new restaurant.
     *
     * @param restaurant    the restaurant to add
     * @param bindingResult the result of the validation
     * @return ResponseEntity containing the added restaurant entity
     */
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

    /**
     * Updates an existing restaurant.
     *
     * @param id             the ID of the restaurant to update
     * @param restaurant     the updated restaurant information
     * @param bindingResult the result of the validation
     * @return ResponseEntity containing the updated restaurant entity
     * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
     */
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

    /**
     * Deletes a restaurant.
     *
     * @param id the ID of the restaurant to delete
     * @return ResponseEntity indicating the success of the deletion operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable final Long id) {

        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
