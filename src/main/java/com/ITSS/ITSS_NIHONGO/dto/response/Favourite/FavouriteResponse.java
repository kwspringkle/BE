package com.ITSS.ITSS_NIHONGO.dto.response.Favourite;

import lombok.Builder;

@Builder
public class FavouriteResponse {
    public int id;
    public int dishId;
    public String dishesname;
    public String restaurantName;
    public int distance;
    public String imageUrl;

    public String description;
    public int likes;
}
