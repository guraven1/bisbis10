package com.att.tdp.bisbis10.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.att.tdp.bisbis10.controller.RestaurantController;
import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class RestaurantModelAssembler implements RepresentationModelAssembler<Restaurant, EntityModel<Restaurant>> {

    @Override
    public EntityModel<Restaurant> toModel(final Restaurant restaurant) {

        return EntityModel.of(restaurant, //
                linkTo(methodOn(RestaurantController.class).getRestaurantById(restaurant.getId())).withSelfRel(),
                linkTo(methodOn(RestaurantController.class).getAllRestaurants()).withRel("restaurants"));
    }
}
