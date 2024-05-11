package com.att.tdp.bisbis10.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;
    private Long dishId;
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private BisOrder bisOrder;
    public OrderItem(){
        super();
    }
    public OrderItem(final Long dishId, final int amount) {
        super();
        this.dishId = dishId;
        this.amount = amount;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Long getDishId() { return dishId; }
    public void setDishId(Long dishId) { this.dishId = dishId; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public BisOrder getOrder() {return bisOrder;}
    public void setOrder(BisOrder bisOrder) {this.bisOrder = bisOrder;}
}