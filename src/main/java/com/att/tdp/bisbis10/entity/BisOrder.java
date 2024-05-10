package com.att.tdp.bisbis10.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class BisOrder {
    @Id
    @UuidGenerator
    private String orderId;
    private Long restaurantId;
    private String status = "IN PROGRESS";
    @OneToMany(mappedBy = "bisOrder", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId",insertable=false, updatable=false)
    Restaurant restaurant;

    public BisOrder(){
        super();
    }
    public BisOrder(Long restaurantId, List<OrderItem> orderItems) {
        super();
        this.orderId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.status = status;
        this.orderItems = orderItems;
    }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public String getStatus() { return status; }
    public void setStatus(String status) {this.status = status; }
    public Restaurant getRestaurant() {return restaurant;}
    public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<com.att.tdp.bisbis10.entity.OrderItem> orderItems) { this.orderItems = orderItems; }
}
