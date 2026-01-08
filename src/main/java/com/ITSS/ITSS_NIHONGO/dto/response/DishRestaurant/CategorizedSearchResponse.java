package com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategorizedSearchResponse {
    private List<RestaurantSearchResult> restaurants;
    private List<SearchResult> dishesByName;
    private List<SearchResult> dishesByIngredients;
}

