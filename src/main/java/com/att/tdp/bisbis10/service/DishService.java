package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.exception.DishNotFoundException;
import com.att.tdp.bisbis10.exception.RestaurantNotFoundException;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing dishes.
 */
@Service
public class DishService {
  @Autowired
  private DishRepository dishRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;

  /**
   * Adds a dish to a restaurant.
   *
   * @param dish the new dish.
   * @param restaurantId the id of the restaurant.
   * @throws RestaurantNotFoundException if the restaurant with the specified ID is not found.
   */
  public void addDish(final Long restaurantId, final Dish dish) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    dish.setRestaurant(restaurant);
    this.dishRepository.save(dish);
  }

  /**
   * Retrieves a dish by its ID.
   *
   * @param dishId the ID of the dish to retrieve
   * @return the retrieved dish
   * @throws DishNotFoundException if the dish with the given ID is not found
   */
  public Dish getDishById(final Long dishId) throws DishNotFoundException {
    return dishRepository.findById(dishId).orElseThrow(() -> new DishNotFoundException(dishId));
  }

  /**
   * Update a dish by ID.
   *
   * @param newDish the new dish.
   * @param dishId the id of the original dish.
   * @throws RestaurantNotFoundException if the restaurant with the specified ID is not found.
   */
  public void updateDish(final Long dishId, final Dish newDish) throws DishNotFoundException {
    Dish oldDish = dishRepository.findById(dishId)
            .orElseThrow(() -> new DishNotFoundException(dishId));
    oldDish.setDescription(newDish.getDescription());
    oldDish.setPrice(newDish.getPrice());
    dishRepository.save(oldDish);
  }

  /**
   * Deletes a dish by ID.
   *
   * @param dishId the id of the dish to delete.
   */
  public void deleteDish(final Long dishId) {
    dishRepository.deleteById(dishId);
  }

  /**
   * Retrieves all dishes from a restaurant.
   *
   * @param restaurantId the restaurant's id.
   * @return a list of dishes.
   */
  public List<Dish> getDishesByRestaurant(final Long restaurantId)
        throws RestaurantNotFoundException {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    return dishRepository.findByRestaurant(restaurant);
  }
}
