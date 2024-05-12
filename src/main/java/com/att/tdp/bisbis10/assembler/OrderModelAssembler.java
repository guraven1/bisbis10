package com.att.tdp.bisbis10.assembler;

import com.att.tdp.bisbis10.controller.DishController;
import com.att.tdp.bisbis10.controller.OrderController;
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
                linkTo(methodOn(OrderController.class).cancelOrderForm(bisOrder.getOrderId()))
                        .withRel("cancelOrder"),
                linkTo(methodOn(RestaurantController.class).getRestaurantById(bisOrder.getRestaurantId()))
                        .withRel("restaurant"),
                linkTo(methodOn(DishController.class).getDishesByRestaurant(bisOrder.getRestaurantId()))
                        .withRel("restaurantDishes"),
                linkTo(methodOn(RestaurantController.class).rateRestaurantForm(bisOrder.getRestaurantId()))
                        .withRel("rateRestaurant"),
                linkTo(methodOn(OrderController.class).getOrderById(bisOrder.getOrderId())).withSelfRel());
    }
}
