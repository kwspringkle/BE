package com.ITSS.ITSS_NIHONGO.repository;

import com.ITSS.ITSS_NIHONGO.model.RestaurantReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Integer> {
    List<RestaurantReview> findByRestaurant_Id(int restaurantId);
    Page<RestaurantReview> findByRestaurant_Id(int restaurantId, Pageable pageable);
    RestaurantReview findFirstByRestaurant_IdOrderByCreatedAtDesc(int restaurantId);
}
