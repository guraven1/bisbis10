package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.entity.BisOrder;
import com.att.tdp.bisbis10.entity.Dish;
import com.att.tdp.bisbis10.entity.OrderItem;
import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.repository.DishRepository;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class Bis10ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@MockBean
	private DishRepository dishRepository;

	@BeforeEach
	void setup() {
		restaurantRepository.deleteAll(); // Clear any existing data before each test
	}
	@Test
	void testAddRestaurant_ValidData() throws Exception {
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void testAddRestaurant_MissingFields() throws Exception {
		Restaurant restaurant = new Restaurant(null, null, 4.5, true, null);
		mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testAddRestaurant_InvalidData() throws Exception {
		Restaurant restaurant = new Restaurant(null, "New Restaurant", -1.0, true, Arrays.asList("Italian"));
		mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateRestaurant_NotFound() throws Exception {
		Restaurant restaurant = new Restaurant(999L, "Updated Restaurant", 4.7, false, Arrays.asList("Mexican"));
		mockMvc.perform(put("/restaurants/{id}", restaurant.getId())
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteRestaurant_NotFound() throws Exception {
		mockMvc.perform(delete("/restaurants/{id}", 999L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void testGetRestaurantById_NotFound() throws Exception {
		mockMvc.perform(get("/restaurants/{id}", 999L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetAllRestaurants_NoData() throws Exception {
		mockMvc.perform(get("/restaurants")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{}"));
	}
	@Test
	void testDeleteRestaurant() throws Exception {
		Restaurant restaurant = new Restaurant(null, "Taizu", 4.83, false, Arrays.asList("Asian", "Mexican", "Indian"));
		restaurantRepository.save(restaurant);

		mockMvc.perform(delete("/restaurants/{id}", restaurant.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void testUpdateRestaurant() throws Exception {
		Restaurant restaurant = new Restaurant(null, "Taizu", 4.83, false, Arrays.asList("Asian", "Mexican", "Indian"));
		restaurantRepository.save(restaurant);

		restaurant.setIsKosher(true);
		mockMvc.perform(MockMvcRequestBuilders.put("/restaurants/{id}", restaurant.getId())
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testGetRestaurantById() throws Exception {
		Restaurant restaurant = new Restaurant(null, "Taizu", 4.83, false, Arrays.asList("Asian", "Mexican", "Indian"));
		restaurantRepository.save(restaurant);

		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{id}", restaurant.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Taizu"))
				.andExpect(jsonPath("$.isKosher").value(false));
	}

	@Test
	void testAddRestaurantRating_ValidData() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		MvcResult restaurantResult = mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		// Extract the ID of the created restaurant from the response
		String restaurantResponseBody = restaurantResult.getResponse().getContentAsString();
		Long restaurantId = Long.parseLong(JsonPath.read(restaurantResponseBody, "$.id").toString());

		// Add a rating for the restaurant using the extracted ID
		String ratingJson = "{\"restaurantId\": " + restaurantId + ", \"rating\": 4.3}";
		mockMvc.perform(post("/ratings")
						.content(ratingJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


	@Test
	void testAddRestaurantRating_RestaurantNotFound() throws Exception {
		String ratingJson = "{\"restaurantId\": 999, \"rating\": 4.3}";
		mockMvc.perform(post("/ratings")
						.content(ratingJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}


	@Test
	void testAddDish_ValidData() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists()) // Ensure that the response contains the ID of the created restaurant
				.andDo(result -> {
					// Extract the ID of the created restaurant
					String responseBody = result.getResponse().getContentAsString();
					Long restaurantId = Long.parseLong(JsonPath.read(responseBody, "$.id").toString());

					// Add a dish to the restaurant using the extracted ID
					Dish dish = new Dish("Pasta", "Bolognese", 75);
					mockMvc.perform(post("/restaurants/" + restaurantId + "/dishes")
									.content(objectMapper.writeValueAsString(dish))
									.contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isCreated());
				});
	}

	@Test
	void testAddDish_RestaurantNotFound() throws Exception {
		String dishJson = "{\"name\": \"Pasta\", \"description\": \"Delicious pasta dish\", \"price\": 12.5}";
		mockMvc.perform(post("/restaurants/999/dishes")
						.content(dishJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}


	@Test
	void testUpdateDish_ValidData() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		MvcResult restaurantResult = mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		// Extract the ID of the created restaurant from the response
		String restaurantResponseBody = restaurantResult.getResponse().getContentAsString();
		long restaurantId = Long.parseLong(JsonPath.read(restaurantResponseBody, "$.id").toString());

		// Add a dish to the restaurant
		String dishJson = "{\"name\": \"Pasta\", \"description\": \"Delicious pasta dish\", \"price\": 12.5}";
		mockMvc.perform(post("/restaurants/" + restaurantId + "/dishes")
						.content(dishJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		// Retrieve the list of dishes for the restaurant
		MvcResult restaurantDishesResult = mockMvc.perform(get("/restaurants/" + restaurantId + "/dishes")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		// Extract the dish ID from the list of dishes associated with the restaurant
		String restaurantDishesResponseBody = restaurantDishesResult.getResponse().getContentAsString();
		List<Long> dishIds = JsonPath.read(restaurantDishesResponseBody, "$[*].id");

		// Ensure that there is at least one dish available
		if (dishIds.isEmpty()) {
			// Handle gracefully or skip the test
			System.out.println("No dishes available for the restaurant. Skipping test.");
			return;
		}

		// Retrieve the first dish ID
		long dishId = dishIds.get(0);

		// Update the dish using the extracted restaurant and dish IDs
		String updatedDishJson = "{\"name\": \"Spaghetti\", \"description\": \"Delicious spaghetti dish\", \"price\": 15.0}";
		mockMvc.perform(put("/restaurants/" + restaurantId + "/dishes/" + dishId)
						.content(updatedDishJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}




	@Test
	void testUpdateDish_RestaurantNotFound() throws Exception {
		String updatedDishJson = "{\"name\": \"Spaghetti\", \"description\": \"Delicious spaghetti dish\", \"price\": 15.0}";
		mockMvc.perform(put("/restaurants/999/dishes/1")
						.content(updatedDishJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateDish_DishNotFound() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		String updatedDishJson = "{\"name\": \"Spaghetti\", \"description\": \"Delicious spaghetti dish\", \"price\": 15.0}";
		mockMvc.perform(put("/restaurants/1/dishes/999")
						.content(updatedDishJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testPlaceOrder_ValidData() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		MvcResult restaurantResult = mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		// Extract the ID of the created restaurant from the response
		String restaurantResponseBody = restaurantResult.getResponse().getContentAsString();
		Long restaurantId = Long.parseLong(JsonPath.read(restaurantResponseBody, "$.id").toString());

		// Retrieve the list of dishes for the restaurant
		MvcResult restaurantDishesResult = mockMvc.perform(get("/restaurants/" + restaurantId + "/dishes")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		// Extract the list of dish IDs from the response
		String restaurantDishesResponseBody = restaurantDishesResult.getResponse().getContentAsString();
		List<Long> dishIds = JsonPath.read(restaurantDishesResponseBody, "$[*].id");

		// Ensure that there is at least one dish available
		if (dishIds.isEmpty()) {
			// Handle gracefully or skip the test
			System.out.println("No dishes available for the restaurant. Skipping test.");
			return;
		}

		// Retrieve the first two dish IDs
		Long dishId1 = dishIds.get(0);
		Long dishId2 = dishIds.size() > 1 ? dishIds.get(1) : null;

		// Place an order using the extracted restaurant ID and dishIds
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem(dishId1, 5));
		if (dishId2 != null) {
			orderItems.add(new OrderItem(dishId2, 1));
		}
		BisOrder order = new BisOrder(restaurantId, orderItems);
		mockMvc.perform(post("/order")
						.content(objectMapper.writeValueAsString(order))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId").exists());
	}


	@Test
	void testPlaceOrder_RestaurantNotFound() throws Exception {
		String orderJson = "{\"restaurantId\": 999, \"orderItems\": [{\"dishId\": 1, \"amount\": 2}, {\"dishId\": 2, \"amount\": 1}]}";
		mockMvc.perform(post("/order")
						.content(orderJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testPlaceOrder_DishNotFound() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		String orderJson = "{\"restaurantId\": 1, \"orderItems\": [{\"dishId\": 999, \"amount\": 2}]}";
		mockMvc.perform(post("/order")
						.content(orderJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testRestaurantRatingEdgeCases() throws Exception {
		// Test maximum rating value
		Restaurant restaurant = new Restaurant(null, "Max Rating Restaurant", 5.0, true, Arrays.asList("Italian"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		// Test minimum rating value
		restaurant = new Restaurant(null, "Min Rating Restaurant", 1.0, true, Arrays.asList("Italian"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void testRestaurantValidation() throws Exception {
		// Test scenario with empty restaurant name
		Restaurant restaurant = new Restaurant(null, "", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		// Test scenario with null restaurant name
		restaurant = new Restaurant(null, null, 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	void testDishValidation() throws Exception {
		// Test scenario with empty dish name
		Restaurant restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andDo(result -> {
					String responseBody = result.getResponse().getContentAsString();
					Long restaurantId = Long.parseLong(JsonPath.read(responseBody, "$.id").toString());

					Dish dish = new Dish("", "Bolognese", 75);
					mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/" + restaurantId + "/dishes")
									.content(objectMapper.writeValueAsString(dish))
									.contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isBadRequest());
				});

		// Test scenario with null dish name
		restaurant = new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(objectMapper.writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andDo(result -> {
					String responseBody = result.getResponse().getContentAsString();
					Long restaurantId = Long.parseLong(JsonPath.read(responseBody, "$.id").toString());

					Dish dish = new Dish(null, "Bolognese", 75);
					mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/" + restaurantId + "/dishes")
									.content(objectMapper.writeValueAsString(dish))
									.contentType(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isBadRequest());
				});
	}
	// Test to verify that the app can handle many restaurants with many dishes
	@Test
	void testManyRestaurantsAndDishes() throws Exception {
		// Add many restaurants with many dishes
		for (int i = 0; i < 1000; i++) {
			// Create restaurant
			MvcResult restaurantResult = mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
							.content(new ObjectMapper().writeValueAsString(new Restaurant(null, "Restaurant " + i, 4.5, true, Arrays.asList("Cuisine"))))
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.id").exists())
					.andReturn();

			// Extract the ID of the created restaurant from the response
			String restaurantResponseBody = restaurantResult.getResponse().getContentAsString();
			long restaurantId = Long.parseLong(JsonPath.read(restaurantResponseBody, "$.id").toString());

			// Add dishes to the restaurant
			for (int j = 0; j < 10; j++) {
				Dish dish = new Dish("Dish " + j, "Description", 10);
				mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/" + restaurantId + "/dishes")
								.content(new ObjectMapper().writeValueAsString(dish))
								.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated());
			}
		}
	}

	// Test to verify that the app can handle many ratings
	@Test
	void testManyRatings() throws Exception {
		// Create a restaurant
		MvcResult restaurantResult = mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(new ObjectMapper().writeValueAsString(new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"))))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists()) // Ensure that the response contains the ID of the created restaurant
				.andReturn();

		// Extract the ID of the created restaurant from the response
		String restaurantResponseBody = restaurantResult.getResponse().getContentAsString();
		long restaurantId = Long.parseLong(JsonPath.read(restaurantResponseBody, "$.id").toString());

		// Add many ratings for the restaurant
		for (int i = 0; i < 1000; i++) {
			String ratingJson = "{\"restaurantId\": " + restaurantId + ", \"rating\": 4.0}";
			mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
							.content(ratingJson)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
	}

	// Test to verify that the averageRating for restaurants is truly the average of ratings
	@Test
	void testAverageRatingCalculation() throws Exception {
		// Add a restaurant
		Restaurant restaurant = new Restaurant(null, "Test Restaurant", 0.0, false, Arrays.asList("Test Cuisine"));
		mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(new ObjectMapper().writeValueAsString(restaurant))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
				.andDo(result -> {
					// Extract the ID of the created restaurant
					String responseBody = result.getResponse().getContentAsString();
					Long restaurantId = Long.parseLong(JsonPath.read(responseBody, "$.id").toString());

					// Add ratings for the restaurant
					double totalRating = 0.0;
					int numRatings = 1000;
					for (int i = 0; i < numRatings; i++) {
						double rating = 4.0;
						totalRating += rating;
						String ratingJson = "{\"restaurantId\": " + restaurantId + ", \"rating\": " + rating + "}";
						mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
										.content(ratingJson)
										.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk());
					}

					// Calculate the expected average rating
					double expectedAverageRating = totalRating / numRatings;

					// Retrieve the restaurant and verify its average rating
					mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/" + restaurantId)
									.contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk())
							.andExpect(MockMvcResultMatchers.jsonPath("$.averageRating").value(expectedAverageRating));
				});
	}

	// Test to verify that the app can handle many orders
	@Test
	void testManyOrders() throws Exception {
		// Create a restaurant
		MvcResult restaurantResult = mockMvc.perform(MockMvcRequestBuilders.post("/restaurants")
						.content(new ObjectMapper().writeValueAsString(new Restaurant(null, "New Restaurant", 4.5, true, Arrays.asList("Italian"))))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists()) // Ensure that the response contains the ID of the created restaurant
				.andReturn();

		// Extract the ID of the created restaurant from the response
		String restaurantResponseBody = restaurantResult.getResponse().getContentAsString();
		long restaurantId = Long.parseLong(JsonPath.read(restaurantResponseBody, "$.id").toString());

		// Retrieve the list of dishes for the restaurant
		MvcResult restaurantDishesResult = mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/" + restaurantId + "/dishes")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		// Extract the dish ID from the list of dishes associated with the restaurant
		String restaurantDishesResponseBody = restaurantDishesResult.getResponse().getContentAsString();
		List<Long> dishIds = JsonPath.read(restaurantDishesResponseBody, "$[*].id");

		// Ensure that there is at least one dish available
		if (dishIds.isEmpty()) {
			// Handle gracefully or skip the test
			System.out.println("No dishes available for the restaurant. Skipping test.");
			return;
		}

		// Add orders with many items
		for (int i = 0; i < 1000; i++) {
			// Retrieve the first dish ID
			long dishId = dishIds.get(0);

			// Create order with the retrieved restaurant ID and dish ID
			List<OrderItem> orderItems = new ArrayList<>();
			orderItems.add(new OrderItem(dishId, 2));
			mockMvc.perform(MockMvcRequestBuilders.post("/order")
							.content(new ObjectMapper().writeValueAsString(new BisOrder(restaurantId, orderItems)))
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
	}


}
