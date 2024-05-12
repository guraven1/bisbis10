package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Places a new order.
     *
     * @param bisOrder The new order to place.
     */
    public void placeOrder(final BisOrder bisOrder) {
        orderRepository.save(bisOrder);
    }

}
