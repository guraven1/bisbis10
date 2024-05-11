package com.att.tdp.bisbis10.service;

import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    public void addDish(final Long restaurantId, final Dish dish) {
        Optional<Restaurant> parentRestaurant = restaurantRepository.findById(restaurantId);
        if (parentRestaurant.isPresent()){
            dish.setRestaurant(parentRestaurant.get());
            this.dishRepository.save(dish);
        }
    }

    public void updateDish(final Long dishId, final Dish changed_dish) {
        Optional<Dish> optionalDish = dishRepository.findById(dishId);
        if (optionalDish.isPresent()) {
            Dish dish = optionalDish.get();
            dish.setDescription(changed_dish.getDescription());
            dish.setPrice(changed_dish.getPrice());
            dishRepository.save(dish);
        }
    }

    public void deleteDish(final Long restaurantId, final Long dishId) {
        dishRepository.deleteById(dishId);
    }

    public List<Dish> getDishesByRestaurant(final Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.map(value -> dishRepository.findByRestaurant(value)).orElse(null);
    }
}
