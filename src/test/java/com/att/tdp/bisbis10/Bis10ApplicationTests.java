package com.att.tdp.bisbis10;

import com.att.tdp.bisbis10.entity.Restaurant;
import com.att.tdp.bisbis10.repository.RestaurantRepository;
import com.att.tdp.bisbis10.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
				.andExpect(status().isNotFound());
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

}
