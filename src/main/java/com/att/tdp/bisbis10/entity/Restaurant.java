package com.att.tdp.bisbis10.entity;

import java.text.DecimalFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double averageRating;
    private boolean isKosher;
    @ElementCollection
    private List<String> cuisines;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Dish> dishes;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Rating> ratings;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<BisOrder> bisOrders;
    public Restaurant() {
        super();
    }
    public Restaurant(Long id, String name, Double averageRating, Boolean isKosher, List<String> cuisines) {
        super();
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.isKosher = isKosher;
        this.cuisines = cuisines;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name) {this.name = name;}

    public Double getAverageRating(){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(averageRating));
    }

    public void setAverageRating(Double averageRating){this.averageRating = averageRating;}

    public boolean getIsKosher(){return this.isKosher;}

    public void setIsKosher(boolean isKosher){this.isKosher = isKosher;}

    public List<String> getCuisines(){return cuisines;}

    public void setCuisines(List<String> cuisines){this.cuisines = cuisines;}

    public List<Dish> getDishes(){return dishes;}

    public void setDishes(List<Dish> dishes){this.dishes = dishes;}
}

