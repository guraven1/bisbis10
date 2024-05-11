package com.att.tdp.bisbis10.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Restaurant restaurant;
    private Double rating;

    public Rating(){
        super();
    }
    public Rating(final Long restaurantId, final Double rating){
        super();
        this.restaurantId = restaurantId;
        this.rating = rating;
    }
    public int getId(){return id;}
    public void setId(final int id){this.id = id;}
    public Long getRestaurantId(){return restaurantId;}
    public void setRestaurantId(final Long restaurantId){this.restaurantId = restaurantId;}
    public Restaurant getRestaurant() {return restaurant;}
    public void setRestaurant(final Restaurant restaurant) {this.restaurant = restaurant;}
    public Double getRating(){return rating;}
    public void setRating(final Double rating){this.rating = rating;}
}
