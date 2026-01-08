package com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResult {
    private int dishRestaurantId;
    private String dishName;
    private String restaurantName;
    private float price;
    private float rating;
    private String imageUrl;
}

