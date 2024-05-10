package com.att.tdp.bisbis10.validators;

import com.att.tdp.bisbis10.entity.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class OrdersValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BisOrder.class.equals(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        BisOrder bisOrder = (BisOrder) obj;
        if (bisOrder.getRestaurantId() == null) {
            errors.rejectValue("restaurantId", "restaurantId.empty", "RestaurantId must not be empty");
        }
        List<OrderItem> itemList = bisOrder.getOrderItems();
        if (itemList == null || itemList.isEmpty()){
            errors.rejectValue("orderItems", "orderItems.empty", "orderItems must not be empty");
        }
    }

}
