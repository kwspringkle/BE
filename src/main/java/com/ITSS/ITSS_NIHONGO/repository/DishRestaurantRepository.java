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

    // Search dishes by name only
    @Query("SELECT dr FROM DishRestaurant dr WHERE " +
           "LOWER(dr.dish.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DishRestaurant> searchDishesByName(@Param("keyword") String keyword);

    // Search dishes by ingredients
    @Query("SELECT dr FROM DishRestaurant dr WHERE " +
           "LOWER(dr.dish.ingredients) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DishRestaurant> searchDishesByIngredients(@Param("keyword") String keyword);

    Optional<DishRestaurant> findByDishIdAndRestaurantId(int dishId, int restaurantId);
}
