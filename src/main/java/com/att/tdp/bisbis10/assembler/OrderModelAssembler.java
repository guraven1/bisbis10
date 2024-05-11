package com.att.tdp.bisbis10.assembler;

import com.att.tdp.bisbis10.controller.RestaurantController;
import com.att.tdp.bisbis10.entity.BisOrder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<BisOrder, EntityModel<BisOrder>> {

    public EntityModel<BisOrder> toModel(final BisOrder bisOrder) {

        // Unconditional links to single-item resource and aggregate root

        return EntityModel.of(bisOrder, //
                linkTo(methodOn(RestaurantController.class).
                        getRestaurantById(bisOrder.getRestaurantId())).withSelfRel(),
                linkTo(methodOn(RestaurantController.class).getAllRestaurants()).withRel("restaurants"));
    }
}
