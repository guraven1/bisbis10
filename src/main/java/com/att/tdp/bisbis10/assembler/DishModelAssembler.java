package com.att.tdp.bisbis10.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.att.tdp.bisbis10.controller.DishController;
import com.att.tdp.bisbis10.controller.RestaurantController;
import com.att.tdp.bisbis10.entity.Dish;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler class for converting Dish entities into EntityModel instances with HATEOAS links.
 */
@Component
public class DishModelAssembler implements RepresentationModelAssembler<Dish, EntityModel<Dish>> {

  /**
   * Converts a Dish entity into an EntityModel with self and additional links.
   *
   * @param dish the Dish entity to be converted
   * @return EntityModel representing the Dish with associated links
   */
  @Override
  public EntityModel<Dish> toModel(final Dish dish) {

    return EntityModel.of(dish, //
            linkTo(methodOn(RestaurantController.class).getRestaurantById(dish.getRestaurant()
                    .getId())).withRel("Back To Restaurant"),
            linkTo(methodOn(DishController.class)
                    .getDish(dish.getRestaurant().getId(), dish.getId())).withSelfRel(),
            linkTo(methodOn(RestaurantController.class).getAllRestaurants())
                    .withRel("All Restaurants"),
            linkTo(methodOn(RestaurantController.class).placeOrderForm(dish.getRestaurant()
                    .getId())).withRel("Place An Order"),
            linkTo(methodOn(RestaurantController.class).rateRestaurantForm(dish.getRestaurant()
                    .getId())).withRel("Rate The Restaurant"));
  }
}
