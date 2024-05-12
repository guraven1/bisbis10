package com.att.tdp.bisbis10.repository;

import com.att.tdp.bisbis10.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Rating entities.
 * Provides methods to perform CRUD operations and custom queries on the Rating entity.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

  /**
   * Calculates the average rating of a restaurant based on its ID.
   *
   * @param restaurantId the ID of the restaurant
   * @return the average rating of the restaurant
   */
  @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.restaurantId = :restaurantId")
  Double calculateAverageRatingByRestaurantId(@Param("restaurantId") Long restaurantId);
}
