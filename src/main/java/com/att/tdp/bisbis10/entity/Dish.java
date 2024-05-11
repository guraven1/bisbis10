package com.att.tdp.bisbis10.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;
    private String name;
    private String description;
    private int price;

    public Dish(){
        super();
    }

    public Dish(final String name, final String description, final int price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() { return id; }

    public void setId(final int id) { this.id = id; }

    public Restaurant getRestaurant() { return restaurant; }

    public void setRestaurant(final Restaurant restaurant) { this.restaurant = restaurant; }

    public String getName() { return name; }

    public void setName(final String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(final String description) { this.description = description; }

    public int getPrice() { return price; }

    public void setPrice(final int price) { this.price = price; }
}
