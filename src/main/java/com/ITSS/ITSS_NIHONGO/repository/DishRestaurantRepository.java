package com.ITSS.ITSS_NIHONGO.repository;

import com.ITSS.ITSS_NIHONGO.model.DishRestaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DishRestaurantRepository extends JpaRepository<DishRestaurant, Integer> {
    List<DishRestaurant> findByRestaurant_Id(int restaurantId);

    List<DishRestaurant> findByDish_Id(int dishId);

    Optional<DishRestaurant> findByDish_IdAndRestaurant_Id(int dishId, int restaurantId);

    @Query("SELECT dr FROM DishRestaurant dr WHERE " +
           "LOWER(dr.dish.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(dr.restaurant.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<DishRestaurant> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Optional<DishRestaurant> findByDishIdAndRestaurantId(int dishId, int restaurantId);
}
