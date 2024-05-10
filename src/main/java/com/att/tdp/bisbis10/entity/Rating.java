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
    public Rating(Long restaurantId, Double rating){
        super();
        this.restaurantId = restaurantId;
        this.rating = rating;
    }
    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public Long getRestaurantId(){return restaurantId;}
    public void setRestaurantId(Long restaurantId){this.restaurantId = restaurantId;}
    public Restaurant getRestaurant() {return restaurant;}
    public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}
    public Double getRating(){return rating;}
    public void setRating(Double rating){this.rating = rating;}
}
