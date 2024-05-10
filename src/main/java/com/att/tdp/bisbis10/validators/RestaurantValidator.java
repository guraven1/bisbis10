package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RestaurantValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.equals(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Restaurant restaurant = (Restaurant) obj;
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            errors.rejectValue("name", "name.empty", "Name must not be empty");
        }
        if (restaurant.getAverageRating() == null || restaurant.getAverageRating() < 0) {
            errors.rejectValue("averageRating", "averageRating.negative", "averageRating must be positive");
        }
        if (restaurant.getCuisines() == null || restaurant.getCuisines().isEmpty()) {
            errors.rejectValue("cuisines", "cuisines.empty", "Cuisines must not be empty");
        }
    }

    public void validateCuisines(Object obj, Errors errors) {
        Restaurant restaurant = (Restaurant) obj;
        if (restaurant.getCuisines() == null || restaurant.getCuisines().isEmpty()) {
            errors.rejectValue("cuisines", "cuisines.empty", "Cuisines must not be empty");
        }
    }

}
