package com.att.tdp.bisbis10.repository;

import com.att.tdp.bisbis10.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Restaurant entities.
 * Provides methods to perform CRUD operations and custom queries on the Restaurant entity.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    /**
     * Retrieves a list of restaurants based on the specified cuisine.
     *
     * @param cuisine the cuisine to search for
     * @return a list of restaurants that serve the specified cuisine
     */
    List<Restaurant> findByCuisinesContaining(final String cuisine);
}
