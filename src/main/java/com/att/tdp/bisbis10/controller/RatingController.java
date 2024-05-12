package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.assembler.RestaurantModelAssembler;
import com.att.tdp.bisbis10.entity.Rating;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.service.RatingService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.RatingValidator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling rating-related operations.
 */
@RestController
public class RatingController {
  @Autowired
  private RatingService ratingService;
  @Autowired
  private RestaurantService restaurantService;
  @Autowired
  private RatingValidator validator;
  @Autowired
  private RestaurantModelAssembler assembler;

  /**
   * Adds a new rating for a restaurant.
   *
   * @param ratingData    the rating data to add
   * @param bindingResult the result of the validation
   * @return ResponseEntity containing a message indicating
    the success of the operation or any validation errors
   */
  @PostMapping("/ratings")
  public ResponseEntity<String>
        addRating(@Valid @RequestBody final Rating ratingData,
                                                             final BindingResult bindingResult)
            throws RestaurantNotFoundException {
    validator.validate(ratingData, bindingResult);
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Validation failed: " + bindingResult.getAllErrors());
    }
    Restaurant restaurant = restaurantService.getRestaurantById(ratingData.getRestaurantId());
    ratingService.addRating(ratingData, restaurant);
    return new ResponseEntity<>(HttpStatus.OK);  }
}
