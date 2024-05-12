package com.att.tdp.bisbis10.repository;

import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Dish entities.
 * Provides methods to perform CRUD operations on the Dish entity.
 */
public interface DishRepository extends JpaRepository<Dish, Long> {

    /**
     * Retrieves a list of dishes by restaurant.
     *
     * @param restaurant the restaurant for which to retrieve dishes
     * @return a list of dishes belonging to the specified restaurant
     */
    List<Dish> findByRestaurant(final Restaurant restaurant);
}
