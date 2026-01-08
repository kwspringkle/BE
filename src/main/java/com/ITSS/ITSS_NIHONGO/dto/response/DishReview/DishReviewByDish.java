package com.ITSS.ITSS_NIHONGO.dto.response.DishReview;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class DishReviewByDish {
    public int dishReviewId;
    public String fullName;
    public String dishesName;
    public String comment;
    public float rate;
    public LocalDateTime date;
    public String avatar;
}
