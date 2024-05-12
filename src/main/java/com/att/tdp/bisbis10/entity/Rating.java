package com.att.tdp.bisbis10.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class representing a rating for a restaurant.
 */
@Entity
@Table(name = "rating")
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "restaurant_id")
  private Long restaurantId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id", referencedColumnName = "id",
        insertable = false, updatable = false)
  private Restaurant restaurant;
  private Double rating;

  public Rating() {
    super();
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public Long getRestaurantId() {
    return restaurantId;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(final Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public Double getRating() {
    return rating;
  }
}
