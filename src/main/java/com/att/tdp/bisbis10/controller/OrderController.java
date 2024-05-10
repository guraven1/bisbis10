package com.att.tdp.bisbis10.controller;

import com.att.tdp.bisbis10.DTO.OrderDTO;
import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.OrderItem;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.service.DishService;
import com.att.tdp.bisbis10.service.OrderService;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.att.tdp.bisbis10.validators.OrderItemValidator;
import com.att.tdp.bisbis10.validators.OrdersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DishService dishService;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrdersValidator validator;
    @Autowired
    private OrderItemValidator itemValidator;
    @PostMapping
    public ResponseEntity<Object> placeOrder(@Valid @RequestBody BisOrder bisOrder, BindingResult bindingResult) {
        validator.validate(bisOrder, bindingResult);
        Restaurant restaurant = restaurantService.getRestaurantById(bisOrder.getRestaurantId());
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
        if (bisOrder.getOrderItems() != null){
            for (OrderItem orderItem : bisOrder.getOrderItems()){
                itemValidator.validate(orderItem, bindingResult);
                Optional<Dish> dish = dishRepository.findById(orderItem.getDishId());
                if (dish.isEmpty() || !restaurant.getDishes().contains(dish.get())){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).
                            body(String.format("Dish: %d not in the menu", orderItem.getDishId()));
                }
            }
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().
                    body("Validation failed: " + bindingResult.getAllErrors());
        }
        orderService.placeOrder(bisOrder);
        bisOrder.setStatus("COMPLETE");
        OrderDTO orderResponse = new OrderDTO();
        orderResponse.setOrderId(bisOrder.getOrderId());
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Object> cancelOrder(@Valid @RequestBody BisOrder bisOrder, BindingResult bindingResult) {
        if (bisOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
        if (!bisOrder.getStatus().equals("IN PROGRESS")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be cancelled because it is not in progress");
        }
        bisOrder.setStatus("CANCELLED");
        orderService.cancelOrder(bisOrder);
        return ResponseEntity.ok("Order cancelled successfully");
    }
}
