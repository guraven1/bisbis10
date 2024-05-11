package com.att.tdp.bisbis10.assembler;

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
                        getId())).withSelfRel(),
                linkTo(dish.getRestaurant().getDishes()).withRel("dishes"));
    }
}
