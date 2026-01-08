package com.ITSS.ITSS_NIHONGO.dto.response.Restaurant;

import lombok.Builder;

@Builder
public class RestaurantResponse {
    public int id;
    public String name;
    public int distance;
    public int minprice;
    public int maxprice;
    public String imageUrl;
    public String address;
    public String phone;
    public String description;
    public float rate;
    public String openTime;
    public  String closeTime;
    public String review;
}
