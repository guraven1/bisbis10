package com.att.tdp.bisbis10.entity;

import java.text.DecimalFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity class representing a restaurant.
 */
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

    /**
     * Default constructor.
     */
    public Restaurant() {
        super();
    }

    /**
     * Parameterized constructor to initialize a restaurant.
     *
     * @param id            the ID of the restaurant
     * @param name          the name of the restaurant
     * @param averageRating the average rating of the restaurant
     * @param isKosher      whether the restaurant is kosher
     * @param cuisines      the cuisines served by the restaurant
     */
    public Restaurant(final Long id, final String name, final Double averageRating, final Boolean isKosher,
                      final List<String> cuisines) {
        super();
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.isKosher = isKosher;
        this.cuisines = cuisines;
    }

    public Long getId(){return id;}

    public void setId(final Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(final String name) {this.name = name;}

    public Double getAverageRating(){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(averageRating));
    }

    public void setAverageRating(final Double averageRating){ this.averageRating = averageRating; }

    public void setIsKosher(final boolean isKosher){ this.isKosher = isKosher; }

    public boolean getIsKosher(){ return this.isKosher; }

    public List<String> getCuisines(){ return cuisines;}

    public void setCuisines(final List<String> cuisines){ this.cuisines = cuisines; }

    public List<Dish> getDishes(){ return dishes; }
}
