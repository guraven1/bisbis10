package com.att.tdp.bisbis10.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.att.tdp.bisbis10.controller.DishController;
import com.att.tdp.bisbis10.controller.OrderController;
import com.att.tdp.bisbis10.controller.RatingController;
import com.att.tdp.bisbis10.controller.RestaurantController;
import com.att.tdp.bisbis10.entity.Rating;
import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler class for converting Restaurant entities into EntityModel instances with HATEOAS links.
 */
@Component
public class RestaurantModelAssembler implements RepresentationModelAssembler<Restaurant, EntityModel<Restaurant>> {

    /**
     * Converts a Restaurant entity into an EntityModel with self and collection links.
     *
     * @param restaurant the Restaurant entity to be converted
     * @return EntityModel representing the Restaurant with associated links
     */
    @Override
    public EntityModel<Restaurant> toModel(final Restaurant restaurant) {

        return EntityModel.of(restaurant, //
                linkTo(methodOn(RestaurantController.class).getRestaurantById(restaurant.getId())).withSelfRel(),
                linkTo(methodOn(RestaurantController.class).getAllRestaurants()).withRel("restaurants"),
                linkTo(methodOn(DishController.class).getDishesByRestaurant(restaurant.getId())).withRel("dishes"),
                linkTo(methodOn(RestaurantController.class).placeOrderForm(restaurant.getId())).withRel("placeOrder"),
                linkTo(methodOn(RestaurantController.class).rateRestaurantForm(restaurant.getId())).withRel("rateRestaurant"));
    }
}
