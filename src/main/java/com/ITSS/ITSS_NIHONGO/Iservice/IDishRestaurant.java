package com.ITSS.ITSS_NIHONGO.Iservice;

import com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant.*;
import com.ITSS.ITSS_NIHONGO.dto.response.Dishes.DishResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDishRestaurant {
    Page<DishRestaurantAll> getDishRestaurantAllList(int page, int size);
    List<DishResponse> getDishRestaurantList(int restaurantId);
    DishRestaurantDetail getDishRestaurantDetail(int id);
    DishRestaurantDetail getDishRestaurantDetail(int dishId, int restaurantId);
    List<RestaurantByDish> getRestaurantByDish(int dishId, int dishRestaurantId);
    Page<SearchResult> searchDishOrRestaurant(String keyword, int page, int size);
    CategorizedSearchResponse searchCategorized(String keyword);
}
