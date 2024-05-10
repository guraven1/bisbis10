package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RatingValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Rating.class.equals(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        Rating rating = (Rating) obj;
        if (rating.getRestaurantId() == null) {
            errors.rejectValue("restaurantId", "restaurantId.empty", "RestaurantId must not be empty");
        }
        Double ratingNumber = rating.getRating();
        if (ratingNumber == null || ratingNumber < 1 || ratingNumber > 5) {
            errors.rejectValue("rating", "rating.invalid", "Rating must be a number ranging from 1 to 5");
        }
    }

}
