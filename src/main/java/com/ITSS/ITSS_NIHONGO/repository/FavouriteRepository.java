package com.ITSS.ITSS_NIHONGO.repository;

import com.ITSS.ITSS_NIHONGO.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer>
{
    List<Favourite> findTop3ByUserIdOrderByCreatedAtDesc(int userId);

    List<Favourite> findByUser_IdOrderByDish_RateDesc(int userId);

    Favourite findByUser_IdAndDish_Id(int userId, int dishId);

    Long countByDish_Id(int id);

    Favourite findByDish_IdAndRestaurant_Id(int dishId, int restaurantId);

    Favourite findByDish_Id(int dishId);

    Optional<Favourite> findByDishIdAndRestaurantId(Integer attr0, Integer attr1);

    Optional<Favourite> findByDishIdAndRestaurantIsNull(Integer dishId);

    Optional<Favourite> findByUserIdAndDishIdAndRestaurantId(int userId, Integer dishId, Integer restaurantId);

    Optional<Favourite> findByUserIdAndDishIdAndRestaurantIsNull(int userId, Integer dishId);
}
