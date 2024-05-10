package com.att.tdp.bisbis10.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.att.tdp.bisbis10.controller.OrderController;
import com.att.tdp.bisbis10.controller.RestaurantController;
import com.att.tdp.bisbis10.entity.BisOrder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<BisOrder, EntityModel<BisOrder>> {

    public EntityModel<BisOrder> toModel(BisOrder bisOrder) {
        BindingResult bindingResult = new org.springframework.validation.BeanPropertyBindingResult(bisOrder, "order");

        // Unconditional links to single-item resource and aggregate root

        EntityModel<BisOrder> orderModel = EntityModel.of(bisOrder, //
                linkTo(methodOn(RestaurantController.class).getRestaurantById(bisOrder.getRestaurantId())).withSelfRel(),
                linkTo(methodOn(RestaurantController.class).getAllRestaurants()).withRel("restaurants"));

        // Conditional links based on state of the order

        if (Objects.equals(bisOrder.getStatus(), "IN PROGRESS")) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(bisOrder, bindingResult)).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).placeOrder(bisOrder, bindingResult)).withRel("complete"));
        }

        return orderModel;
    }

}