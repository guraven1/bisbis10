package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that provides methods to perform CRUD operations on restaurants.
 */
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Retrieves all restaurants.
     *
     * @return List of all restaurants.
     */
    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    /**
     * Retrieves all restaurants that have a certain CUISINE.
     *
     * @param cuisine The cuisine that the restaurants returned have.
     * @return a list of restaurants with the specified cuisine.
     */
    public List<Restaurant> getRestaurantsByCuisine(final String cuisine){
        return restaurantRepository.findByCuisinesContaining(cuisine);
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return The restaurant with the specified ID.
     * @throws RestaurantNotFoundException if the restaurant with the specified ID is not found.
     */
    public Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    /**
     * Adds a new restaurant.
     *
     * @param restaurant The restaurant to add.
     */
    public void addRestaurant(final Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    /**
     * Updates an existing restaurant.
     *
     * @param id          The ID of the restaurant to update.
     * @param newRestaurant The updated restaurant data.
     * @throws RestaurantNotFoundException if the restaurant with the specified ID is not found.
     */
    public void updateRestaurant(final Long id, final Restaurant newRestaurant) throws
            RestaurantNotFoundException {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        existingRestaurant.setCuisines(newRestaurant.getCuisines());
        restaurantRepository.save(existingRestaurant);
    }

    public void updateAverageRating(Long id, double newAverageRating) throws
            RestaurantNotFoundException {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));;
        existingRestaurant.setAverageRating(newAverageRating);
        restaurantRepository.save(existingRestaurant);
        }

    public void deleteRestaurant(final Long id) {
        restaurantRepository.deleteById(id);
    }
}
