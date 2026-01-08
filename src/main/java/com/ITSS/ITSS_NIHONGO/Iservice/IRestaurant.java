package com.ITSS.ITSS_NIHONGO.Iservice;

import com.ITSS.ITSS_NIHONGO.dto.response.Restaurant.RestaurantResponse;
import org.springframework.data.domain.Page;

public interface IRestaurant {
    Page<RestaurantResponse> getRestaurantrecentlyList(int page, int size);
    RestaurantResponse getRestaurantDetail(int id);
}
