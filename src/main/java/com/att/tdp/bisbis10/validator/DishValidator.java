package com.att.tdp.bisbis10.validator;

import com.att.tdp.bisbis10.entity.Dish;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator class for validating Dish objects.
 */
@Component
public class DishValidator implements Validator {

  /**
   * Determines whether the validator supports the given class.
   *
   * @param clazz the class to check for support
   * @return true if the validator supports the class; false otherwise
   */
  @Override
  public boolean supports(final Class<?> clazz) {
    return Dish.class.equals(clazz);
  }

  /**
   * Validates the given object and populates errors if any validation fails.
   *
   * @param obj    the object to validate
   * @param errors the Errors instance to populate with validation errors
   */
  @Override
  public void validate(final Object obj, final Errors errors) {
    Dish dish = (Dish) obj;
    if (dish.getName() == null || dish.getName().isEmpty()) {
      errors.rejectValue("name", "name.empty", "Name must not be empty");
    }
    if (dish.getDescription() == null || dish.getDescription().isEmpty()) {
      errors.rejectValue("description",
                "description.empty", "Description must not be empty");
    }
    if (dish.getPrice() <= 0) {
      errors.rejectValue("price",
                "price.nonPositive", "Price must be larger than zero");
    }
  }

  /**
   * Validates the given object for updates and populates errors if any validation fails.
   *
   * @param obj    the object to validate
   * @param errors the Errors instance to populate with validation errors
   */
  public void validateUpdate(final Object obj, final Errors errors) {
    Dish dish = (Dish) obj;
    if (dish.getDescription() == null || dish.getDescription().isEmpty()) {
      errors.rejectValue("description",
                "description.empty", "Description must not be empty");
    }
    if (dish.getPrice() <= 0) {
      errors.rejectValue("price",
                "price.nonPositive", "Price must be larger than zero");
    }
  }
}
