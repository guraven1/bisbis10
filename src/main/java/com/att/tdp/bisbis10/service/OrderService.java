package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.exception.OrderNotFoundException;
import com.att.tdp.bisbis10.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing orders.
 */
@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;

  /**
   * Places a new order.
   *
   * @param bisOrder The new order to place.
   */
  public void placeOrder(BisOrder bisOrder) {
    orderRepository.save(bisOrder);
  }

  /**
   * Retrieves an order by its ID.
   *
   * @param orderId The ID of the order to retrieve.
   * @return The order if found.
   * @throws OrderNotFoundException if the order with the specified ID is not found.
   */
  public BisOrder getOrderById(final String orderId) throws OrderNotFoundException {
    return orderRepository.findById(orderId)
          .orElseThrow(() -> new OrderNotFoundException(orderId));
  }
}
