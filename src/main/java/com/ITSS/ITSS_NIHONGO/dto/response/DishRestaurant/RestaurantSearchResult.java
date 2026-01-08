package com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantSearchResult {
    private int restaurantId;
    private String restaurantName;
    private String address;
    private int distance;
    private String imageUrl;
    private int minPrice;
    private int maxPrice;
}

