package com.ITSS.ITSS_NIHONGO.dto.response.RestaurantReview;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class RestaurantReviewByRestaurant {
    public int restaurantReviewId;
    public String fullName;
    public String restaurantName;
    public String comment;
    public float rating;
    public LocalDateTime date;
    public String avatar;
}

