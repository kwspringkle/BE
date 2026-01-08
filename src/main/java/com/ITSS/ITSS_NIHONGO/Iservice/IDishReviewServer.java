package com.ITSS.ITSS_NIHONGO.Iservice;

import com.ITSS.ITSS_NIHONGO.dto.request.DishReview.AddDishReview;
import com.ITSS.ITSS_NIHONGO.dto.request.DishReview.UpdateDishReview;
import com.ITSS.ITSS_NIHONGO.dto.response.DishReview.DishReviewByDish;
import com.ITSS.ITSS_NIHONGO.model.DishReview;

import java.util.List;

public interface IDishReviewServer {
    boolean addDishReview(int userId, AddDishReview addDishReview);
    boolean updateDishReview(UpdateDishReview updateDishReview);
    boolean deleteDishReview(int dishReviewId);
    List<DishReviewByDish> getDishReviewByDish(int dishId);
}
