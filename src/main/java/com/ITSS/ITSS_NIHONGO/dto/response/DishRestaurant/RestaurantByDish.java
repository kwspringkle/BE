package com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant;

import lombok.Builder;

@Builder
public class RestaurantByDish {
    public int restaurantId;
    public String restaurantName;
    public String imageUrl;
}
