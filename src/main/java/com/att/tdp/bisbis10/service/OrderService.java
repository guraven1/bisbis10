package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public BisOrder placeOrder(BisOrder bisOrder) {
        return orderRepository.save(bisOrder);
    }

    public void cancelOrder(BisOrder bisOrder) {
        orderRepository.delete(bisOrder);
    }
}
