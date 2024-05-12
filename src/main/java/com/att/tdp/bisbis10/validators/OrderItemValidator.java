package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.OrderItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator class for validating OrderItem objects.
 */
@Component
public class OrderItemValidator implements Validator {

    /**
     * Determines whether the validator supports the given class.
     *
     * @param clazz the class to check for support
     * @return true if the validator supports the class; false otherwise
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return OrderItem.class.equals(clazz);
    }

    /**
     * Validates the given object and populates errors if any validation fails.
     *
     * @param obj    the object to validate
     * @param errors the Errors instance to populate with validation errors
     */
    @Override
    public void validate(final Object obj, final Errors errors) {
        OrderItem orderItems = (OrderItem) obj;
        if (orderItems.getDishId() == null) {
            errors.rejectValue("dishId", "dishId.empty", "DishId must not be empty");
        }
        if (orderItems.getAmount() <= 0) {
            errors.rejectValue("amount",
                    "amount.invalid", "Amount must be greater than zero");
        }
    }
}
