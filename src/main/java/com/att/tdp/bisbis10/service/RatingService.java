package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.RatingRepository;
import com.att.tdp.bisbis10.entity.Rating;
import com.att.tdp.bisbis10.entity.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Adds a rating for a restaurant.
     *
     * @param rating          The new rating of the restaurant to post.
     * @param restaurant the restaurant that the rating is for.
     */
    @Transactional
    public void addRating(final Rating rating, final Restaurant restaurant) {
        rating.setRestaurant(restaurant);
        ratingRepository.save(rating);
        Long restaurantId = rating.getRestaurantId();
        Double newAverageRating = ratingRepository.calculateAverageRatingByRestaurantId(restaurantId);
        restaurantService.updateAverageRating(restaurantId, newAverageRating);
    }
}
