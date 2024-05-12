package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.entity.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator class for validating BisOrder objects.
 */
@Component
public class BisOrderValidator implements Validator {

  /**
   * Determines whether the validator supports the given class.
   *
   * @param clazz the class to check for support
   * @return true if the validator supports the class; false otherwise
   */
  @Override
  public boolean supports(final Class<?> clazz) {
    return BisOrder.class.equals(clazz);
  }

  /**
   * Validates the given object and populates errors if any validation fails.
   *
   * @param obj    the object to validate
   * @param errors the Errors instance to populate with validation errors
   */
  @Override
  public void validate(final Object obj, final Errors errors) {
    BisOrder bisOrder = (BisOrder) obj;
    if (bisOrder.getRestaurantId() == null) {
      errors.rejectValue("restaurantId",
                "restaurantId.empty", "RestaurantId must not be empty");
    }
    List<OrderItem> itemList = bisOrder.getOrderItems();
    if (itemList == null || itemList.isEmpty()) {
      errors.rejectValue("orderItems",
                "orderItems.empty", "orderItems must not be empty");
    }
  }
}
