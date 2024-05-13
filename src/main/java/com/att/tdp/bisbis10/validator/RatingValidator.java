package com.att.tdp.bisbis10.validator;

import com.att.tdp.bisbis10.entity.Rating;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator class for validating Rating objects.
 */
@Component
public class RatingValidator implements Validator {

  /**
   * Determines whether the validator supports the given class.
   *
   * @param clazz the class to check for support
   * @return true if the validator supports the class; false otherwise
   */
  @Override
  public boolean supports(final Class<?> clazz) {
    return Rating.class.equals(clazz);
  }

  /**
   * Validates the given object and populates errors if any validation fails.
   *
   * @param obj    the object to validate
   * @param errors the Errors instance to populate with validation errors
   */
  @Override
  public void validate(final Object obj, final Errors errors) {
    Rating rating = (Rating) obj;
    if (rating.getRestaurantId() == null) {
      errors.rejectValue("restaurantId",
                "restaurantId.empty", "RestaurantId must not be empty");
    }
    Double ratingNumber = rating.getRating();
    if (ratingNumber == null || ratingNumber < 0 || ratingNumber > 5) {
      errors.rejectValue("rating",
                "rating.invalid", "Rating must be a number ranging from 1 to 5");
    }
  }
}
