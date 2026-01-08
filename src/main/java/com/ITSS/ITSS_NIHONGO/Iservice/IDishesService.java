package com.ITSS.ITSS_NIHONGO.Iservice;

import com.ITSS.ITSS_NIHONGO.dto.response.Dishes.DishResponse;

import java.util.List;

public interface IDishesService {
    List<DishResponse> getDishfamousList();
    List<DishResponse> getAllDishfamousList();
    List<DishResponse> findDishWithSameIngredients(int dishId,int restaurantId);
}
