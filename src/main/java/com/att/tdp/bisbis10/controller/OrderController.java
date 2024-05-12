package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.assembler.OrderModelAssembler;
import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.exception.DishNotFoundException;
import com.att.tdp.bisbis10.exception.OrderNotFoundException;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.OrderItem;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.service.DishService;
import com.att.tdp.bisbis10.service.OrderService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.OrderItemValidator;
import com.att.tdp.bisbis10.validators.BisOrderValidator;
import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller class for handling order-related operations.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    /**
     * Constructs a new OrderController with the given OrderService.
     *
     * @param orderService the order service to be used
     */
    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DishService dishService;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private BisOrderValidator validator;
    @Autowired
    private OrderItemValidator itemValidator;
    @Autowired
    private OrderModelAssembler assembler;

    /**
     * Places an order.
     *
     * @param bisOrder      the order data to be placed
     * @param bindingResult the result of the validation
     * @return ResponseEntity containing the placed order or any validation errors
     */
    @PostMapping
    public ResponseEntity<Object> placeOrder(@Valid @RequestBody final BisOrder bisOrder,
                                             BindingResult bindingResult)
            throws RestaurantNotFoundException, DishNotFoundException {
        validator.validate(bisOrder, bindingResult);
        Restaurant restaurant = restaurantService.getRestaurantById(bisOrder.getRestaurantId());

        if (bisOrder.getOrderItems() != null){
            for (OrderItem orderItem : bisOrder.getOrderItems()){
                itemValidator.validate(orderItem, bindingResult);
                Dish dish = dishService.getDishById(orderItem.getDishId());
                if (bindingResult.hasErrors()) {
                    return ResponseEntity.badRequest().
                            body("Validation failed: " + bindingResult.getAllErrors());
                }
            }
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().
                    body("Validation failed: " + bindingResult.getAllErrors());
        }
        orderService.placeOrder(bisOrder);
        EntityModel<BisOrder> orderModel = assembler.toModel(bisOrder);

        return new ResponseEntity<>(orderModel, HttpStatus.OK);
    }

    @GetMapping("/{id}/cancelOrder")
    public ResponseEntity<Void> cancelOrderForm(@PathVariable final String id) {
        // Return a link to the canceling form
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BisOrder>> getOrderById(@PathVariable String id) throws OrderNotFoundException {
            BisOrder order = orderService.getOrderById(id);
            EntityModel<BisOrder> orderModel = assembler.toModel(order);
            return ResponseEntity.ok(orderModel);
    }
}
