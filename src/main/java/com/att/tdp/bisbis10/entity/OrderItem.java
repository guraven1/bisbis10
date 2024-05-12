package com.att.tdp.bisbis10.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

/**
 * Entity class representing an item in an order.
 */
@Entity
public class OrderItem {
  @Id
  @GeneratedValue
  private UUID id;
  private Long dishId;
  private int amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private BisOrder bisOrder;

  /**
   * Default constructor.
   */
  public OrderItem() {
    super();
  }

  /**
   * Parameterized constructor to initialize an order item.
   *
   * @param dishId the ID of the dish
   * @param amount the quantity of the dish in the order
   */
  public OrderItem(final Long dishId, final int amount) {
    super();
    this.dishId = dishId;
    this.amount = amount;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getDishId() {
    return dishId;
  }

  public void setDishId(Long dishId) {
    this.dishId = dishId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public BisOrder getOrder() {
    return bisOrder;
  }

  public void setOrder(BisOrder bisOrder) {
    this.bisOrder = bisOrder;
  }
}
