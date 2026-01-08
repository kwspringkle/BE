package com.ITSS.ITSS_NIHONGO.repository;

import com.ITSS.ITSS_NIHONGO.model.DishReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DishReviewRepository extends JpaRepository<DishReview, Integer> {
    List<DishReview> findByDish_Id(int dishId);

    List<DishReview> findByDish_IdOrderByCreatedAtDesc(int dishId);

    Collection<Object> countByDish_Id(int dishId);
}
