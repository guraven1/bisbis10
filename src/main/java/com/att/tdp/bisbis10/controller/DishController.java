package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.exception.DishNotFoundException;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.DishService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validator.DishValidator;
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
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller class for handling dish-related operations.
 */
@RestController
@RequestMapping("/restaurants/{id}/dishes")
public class DishController {

  private final DishService dishService;

  /**
   * Constructs a new DishController with the given DishService.
   *
   * @param dishService the dish service to be used
   */
  @Autowired
  public DishController(DishService dishService) {
    this.dishService = dishService;
  }

  @Autowired
  private RestaurantService restaurantService;
  // Service for handling restaurant-related business logic

  @Autowired
  private DishValidator validator;
  // Validator for validating dish data

  /**
   * Adds a dish to the specified restaurant.
   *
   * @param restaurantId  the ID of the restaurant to which the dish belongs
   * @param dish          the dish data to be added
   * @param bindingResult the result of the validation
   * @return ResponseEntity containing the added dish or any validation errors
   * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
   */
  @PostMapping
  public ResponseEntity<String> addDish(@PathVariable("id") final Long restaurantId,
                                             @Valid @RequestBody final Dish dish,
                                             final BindingResult bindingResult)
        throws RestaurantNotFoundException {
    validator.validate(dish, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
    }
    dishService.addDish(restaurantId, dish);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Updates a dish of the specified restaurant.
   *
   * @param restaurantId  the ID of the restaurant to which the dish belongs
   * @param dishId        the ID of the dish to be updated
   * @param dish          the dish data to be updated
   * @param bindingResult the result of the validation
   * @return ResponseEntity containing status or any validation errors
   * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
   * @throws DishNotFoundException      if the dish with the given ID is not found
   */
  @PutMapping("/{dishId}")
  public ResponseEntity<String> updateDish(@PathVariable("id") final Long restaurantId,
                                              @PathVariable final Long dishId,
                                              @Valid @RequestBody final Dish dish,
                                                      final BindingResult bindingResult)
          throws RestaurantNotFoundException, DishNotFoundException {
    validator.validateUpdate(dish, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
    }
    dishService.updateDish(dishId, dish);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Deletes a dish from the specified restaurant.
   *
   * @param restaurantId the ID of the restaurant from which the dish will be deleted
   * @param dishId       the ID of the dish to be deleted
   * @return ResponseEntity indicating the success of the deletion
   */
  @DeleteMapping("/{dishId}")
  public ResponseEntity<String> deleteDish(@PathVariable("id") final Long restaurantId,
                                           @PathVariable final Long dishId) {
    dishService.deleteDish(dishId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Retrieves all dishes belonging to the specified restaurant.
   *
   * @param restaurantId the ID of the restaurant
   * @return ResponseEntity containing the list of dishes or an error if the restaurant is not found
   * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
   */
  @GetMapping
  public ResponseEntity<List<Dish>>
      getDishesByRestaurant(@PathVariable("id") final Long restaurantId)
      throws RestaurantNotFoundException {
    List<Dish> dishes = dishService.getDishesByRestaurant(restaurantId);
    return new ResponseEntity<>(dishes, HttpStatus.OK);
  }

  /**
   * Retrieves a dish by its ID.
   *
   * @param restaurantId the ID of the restaurant
   * @param dishId       the ID of the dish to retrieve
   * @return ResponseEntity containing the retrieved dish or an error if the dish is not found
   * @throws RestaurantNotFoundException if the restaurant with the given ID is not found
   * @throws DishNotFoundException      if the dish with the given ID is not found
   */
  @GetMapping("/{dishId}")
  public ResponseEntity<Dish> getDish(@PathVariable("id") final Long restaurantId,
                                                   @PathVariable final Long dishId)
          throws RestaurantNotFoundException, DishNotFoundException {
    Dish dish = dishService.getDishById(dishId);
    return new ResponseEntity<>(dish, HttpStatus.OK);
  }
}
