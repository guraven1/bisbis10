package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderItemValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return OrderItem.class.equals(clazz);
    }
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
