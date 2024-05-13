package com.att.tdp.bisbis10.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * Projection interface for representing restaurant details.
 */
@JsonPropertyOrder({"id", "name", "averageRating", "isKosher", "cuisines"})
public interface RestaurantProjection {

  Long getId();

  String getName();

  double getAverageRating();

  boolean getIsKosher();

  List<String> getCuisines();

}
