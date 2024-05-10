package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByCuisine(String cuisine){
        return restaurantRepository.findByCuisinesContaining(cuisine);
    }
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }
    public void addRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }
    public void updateRestaurant(Long id, Restaurant restaurant){
        Optional<Restaurant> existingRestaurantOptional = restaurantRepository.findById(id);
        if (existingRestaurantOptional.isPresent()){
            existingRestaurantOptional.get().setCuisines(restaurant.getCuisines());
            restaurantRepository.save(existingRestaurantOptional.get());
        }
    }
    public void updateAverageRating(Long restaurantId, double newAverageRating) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        optionalRestaurant.ifPresent(restaurant -> {
            restaurant.setAverageRating(newAverageRating);
            restaurantRepository.save(restaurant);
        });
    }
    public void deleteRestaurant(Long id){
        restaurantRepository.deleteById(id);
    }
}
