package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.projection.RestaurantProjection;
import com.att.tdp.bisbis10.assembler.RestaurantModelAssembler;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validator.RestaurantValidator;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling restaurant-related operations.
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;
  // Service for handling restaurant-related business logic

  @Autowired
  private RestaurantValidator validator;
  // Validator for validating restaurant data

  @Autowired
  private RestaurantModelAssembler assembler;
  // Assembler for creating HATEOAS-compliant representations of restaurant entities

  /**
  * Retrieves all restaurants.
  *
  * @return ResponseEntity containing a collection of restaurant entities
  */
  @GetMapping
  public ResponseEntity<List<RestaurantProjection>> getAllRestaurants() {
    List<RestaurantProjection> restaurants = restaurantService.getAllRestaurants();
    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }

  /**
  * Retrieves restaurants by cuisine.
  *
  * @param cuisine the cuisine to filter by
  * @return ResponseEntity containing a collection of restaurant entities
  */
  @GetMapping(params = "cuisine")
  public ResponseEntity<List<RestaurantProjection>>
      getRestaurantsByCuisine(@RequestParam final String cuisine) {
    List<RestaurantProjection> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  }

  /**
  * Retrieves a restaurant by its ID.
  *
  * @param id the ID of the restaurant to retrieve
  * @return ResponseEntity containing the restaurant entity
  * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
  */
  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> getRestaurantById(@PathVariable final Long id)
            throws RestaurantNotFoundException {
    Restaurant restaurant = restaurantService.getRestaurantById(id);
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }

  /**
  * Adds a new restaurant.
  *
  * @param restaurant    the restaurant to add
  * @param bindingResult the result of the validation
  * @return ResponseEntity containing the added restaurant entity
  */
  @PostMapping
  public ResponseEntity<String>
      addRestaurant(@Valid @RequestBody final Restaurant restaurant,
                    final BindingResult bindingResult) {
    validator.validate(restaurant, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
    }
    restaurantService.addRestaurant(restaurant);
    return new ResponseEntity<>(HttpStatus.CREATED);
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
  public ResponseEntity<String>
      updateRestaurant(@PathVariable final Long id,
                       @Valid @RequestBody final Restaurant restaurant,
             final BindingResult bindingResult)
            throws RestaurantNotFoundException {
    validator.validateCuisines(restaurant, bindingResult);

    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
    }

    restaurantService.updateRestaurant(id, restaurant);

    return new ResponseEntity<>(HttpStatus.OK);
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

  /**
  * Provides a form for placing an order for a restaurant.
  *
  * @param id the ID of the restaurant
  * @return ResponseEntity containing a link to the order placement form
  */
  @GetMapping("/{id}/placeOrder")
  public ResponseEntity<Void> placeOrderForm(@PathVariable final Long id) {
    // Return a link to the order placement form
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
  * Provides a form for rating a restaurant.
  *
  * @param id the ID of the restaurant
  * @return ResponseEntity containing a link to the rating form
  */
  @GetMapping("/{id}/rateRestaurant")
  public ResponseEntity<Void> rateRestaurantForm(@PathVariable final Long id) {
    // Return a link to the rating form
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
