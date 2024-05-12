package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.assembler.OrderModelAssembler;
import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.OrderItem;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.exception.DishNotFoundException;
import com.att.tdp.bisbis10.exception.OrderNotFoundException;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.service.DishService;
import com.att.tdp.bisbis10.service.OrderService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.BisOrderValidator;
import com.att.tdp.bisbis10.validators.OrderItemValidator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling order-related operations.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;
  @Autowired
  private RestaurantService restaurantService;
  @Autowired
  private DishService dishService;
  // Service for handling restaurant-related business logic
  @Autowired
  private DishRepository dishRepository;
  // Service for handling dish-related business logic
  @Autowired
  private BisOrderValidator validator;
  // Repository for accessing dish data
  @Autowired
  private OrderItemValidator itemValidator;
  // Validator for validating order data
  @Autowired
  private OrderModelAssembler assembler;
  // Validator for validating order item data

  /**
  * Constructs a new OrderController with the given OrderService.
  *
  * @param orderService the order service to be used
  */
  @Autowired
  public OrderController(final OrderService orderService) {
    this.orderService = orderService;
  }
  // Assembler for creating HATEOAS-compliant representations of order entities

  /**
  * Places an order.
  *
  * @param bisOrder      the order data to be placed
  * @param bindingResult the result of the validation
  * @return ResponseEntity containing the placed order or any validation errors
  */
  @PostMapping
  public ResponseEntity<Object> placeOrder(@Valid @RequestBody BisOrder bisOrder,
                                             BindingResult bindingResult)
          throws RestaurantNotFoundException, DishNotFoundException {
    validator.validate(bisOrder, bindingResult);
    Restaurant restaurant = restaurantService.getRestaurantById(bisOrder.getRestaurantId());

    if (bisOrder.getOrderItems() != null) {
      for (OrderItem orderItem : bisOrder.getOrderItems()) {
        itemValidator.validate(orderItem, bindingResult);
        Dish dish = dishService.getDishById(orderItem.getDishId());
        if (bindingResult.hasErrors()) {
          return ResponseEntity.badRequest()
                .body("Validation failed: " + bindingResult.getAllErrors());
        }
      }
    }
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
            .body("Validation failed: " + bindingResult.getAllErrors());
    }
    orderService.placeOrder(bisOrder);
    EntityModel<BisOrder> orderModel = assembler.toModel(bisOrder);

    return new ResponseEntity<>(orderModel, HttpStatus.OK);
  }

  /**
  * Generates a cancel order form (Not Implemented!).
  *
  * @param id the ID of the order for which the form is generated
  * @return ResponseEntity indicating the status of the operation
  */
  @GetMapping("/{id}/cancelOrder")
  public ResponseEntity<Void> cancelOrderForm(@PathVariable final String id) {
    // Return a link to the canceling form
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * Retrieves the order with the specified ID.
   *
   * @param id the ID of the order to retrieve
   * @return ResponseEntity containing the order with associated links
   * @throws OrderNotFoundException if the order with the specified ID is not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<BisOrder>>
        getOrderById(@PathVariable String id) throws OrderNotFoundException {
    BisOrder order = orderService.getOrderById(id);
    EntityModel<BisOrder> orderModel = assembler.toModel(order);
    return ResponseEntity.ok(orderModel);
  }
}
