package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator class for validating Restaurant objects.
 */
@Component
public class RestaurantValidator implements Validator {

  /**
   * Determines whether the validator supports the given class.
   *
   * @param clazz the class to check for support
   * @return true if the validator supports the class; false otherwise
   */
  @Override
  public boolean supports(final Class<?> clazz) {
    return Restaurant.class.equals(clazz);
  }

  /**
   * Validates the given object and populates errors if any validation fails.
   *
   * @param obj    the object to validate
   * @param errors the Errors instance to populate with validation errors
   */
  @Override
  public void validate(final Object obj, final Errors errors) {
    Restaurant restaurant = (Restaurant) obj;
    if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
      errors.rejectValue("name", "name.empty",
            "Name must not be empty");
    }
    if (restaurant.getAverageRating() == null
            || restaurant.getAverageRating() < 0 || restaurant.getAverageRating() > 5) {
      errors.rejectValue("averageRating",
                "averageRating.bounds",
            "averageRating must be between 0 and 5");
    }
    if (restaurant.getCuisines() == null || restaurant.getCuisines().isEmpty()) {
      errors.rejectValue("cuisines", "cuisines.empty",
            "Cuisines must not be empty");
    }
  }

  /**
   * Validates the cuisines of the given object and populates errors if empty.
   *
   * @param obj    the object to validate
   * @param errors the Errors instance to populate with validation errors
   */
  public void validateCuisines(final Object obj, final Errors errors) {
    Restaurant restaurant = (Restaurant) obj;
    if (restaurant.getCuisines() == null || restaurant.getCuisines().isEmpty()) {
      errors.rejectValue("cuisines", "cuisines.empty", "Cuisines must not be empty");
    }
  }
}
