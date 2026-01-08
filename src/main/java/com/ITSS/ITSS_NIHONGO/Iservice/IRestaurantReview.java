package com.ITSS.ITSS_NIHONGO.Iservice;

import com.ITSS.ITSS_NIHONGO.dto.request.RestaurantReview.AddRestaurantReview;
import com.ITSS.ITSS_NIHONGO.dto.request.RestaurantReview.UpdateRestaurantReview;
import com.ITSS.ITSS_NIHONGO.dto.response.RestaurantReview.RestaurantReviewByRestaurant;

import java.util.List;

public interface IRestaurantReview {
    boolean addRestaurantReview(int userId, AddRestaurantReview addRestaurantReview);
    boolean updateRestaurantReview(UpdateRestaurantReview updateRestaurantReview);
    boolean deleteRestaurantReview(int restaurantReviewId);
    List<RestaurantReviewByRestaurant> getRestaurantReviewByRestaurant(int restaurantId);
}
