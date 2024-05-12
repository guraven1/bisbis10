package com.att.tdp.bisbis10.assembler;

import com.att.tdp.bisbis10.controller.DishController;
import com.att.tdp.bisbis10.controller.OrderController;
import com.att.tdp.bisbis10.controller.RestaurantController;
import com.att.tdp.bisbis10.entity.Dish;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DishModelAssembler implements RepresentationModelAssembler<Dish, EntityModel<Dish>> {

    @Override
    public EntityModel<Dish> toModel(final Dish dish) {

        return EntityModel.of(dish, //
                linkTo(methodOn(RestaurantController.class).getRestaurantById(dish.getRestaurant().
                        getId())).withRel("Back To Restaurant"),
                linkTo(methodOn(DishController.class).
                        getDish(dish.getRestaurant().getId(), dish.getId())).withSelfRel(),
                linkTo(methodOn(RestaurantController.class).getAllRestaurants()).withRel("All Restaurants"),
                linkTo(methodOn(RestaurantController.class).placeOrderForm(dish.getRestaurant().
                        getId())).withRel("Place An Order"),
                linkTo(methodOn(RestaurantController.class).rateRestaurantForm(dish.getRestaurant().
                        getId())).withRel("Rate The Restaurant"));
    }
}
