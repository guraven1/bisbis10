package com.att.tdp.bisbis10.assembler;

import com.att.tdp.bisbis10.DTO.BisOrderDTO;
import com.att.tdp.bisbis10.controller.OrderController;
import com.att.tdp.bisbis10.controller.RestaurantController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembler class for converting BisOrder entities into EntityModel instances with HATEOAS links.
 */
@Component
public class OrderModelAssembler implements RepresentationModelAssembler<BisOrderDTO,
        EntityModel<BisOrderDTO>> {

  /**
   * Converts a BisOrder entity into an EntityModel with self and additional links.
   *
   * @param bisOrderDTO the bisOrderDTO entity to be converted
   * @return EntityModel representing the BisOrder with associated links
   */
  public EntityModel<BisOrderDTO> toModel(final BisOrderDTO bisOrderDTO) {

    // Unconditional links to single-item resource and aggregate root

    return EntityModel.of(bisOrderDTO, //
            linkTo(methodOn(OrderController.class).cancelOrderForm(bisOrderDTO
                    .getOrderId()))
                    .withRel("Cancel Order"),
            linkTo(methodOn(RestaurantController.class).getAllRestaurants())
                    .withRel("All Restaurants"),
            linkTo(methodOn(OrderController.class).getOrderById(bisOrderDTO.getOrderId()))
                    .withSelfRel());
  }
}
