package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DishValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return Dish.class.equals(clazz);
    }
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
