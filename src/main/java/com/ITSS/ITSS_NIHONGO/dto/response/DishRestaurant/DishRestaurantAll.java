package com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant;

import lombok.Builder;

@Builder
public class DishRestaurantAll {
    public int id;
    public String dishesname;
    public String restaurantname;
    public int restaurantId;
    public String restaurantAddress;
    public int distance;
    public String imageUrl;
}
