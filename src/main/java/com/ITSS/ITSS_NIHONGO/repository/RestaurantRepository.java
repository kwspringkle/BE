package com.ITSS.ITSS_NIHONGO.repository;

import com.ITSS.ITSS_NIHONGO.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByDistanceLessThan(int distanceIsLessThan);
    Page<Restaurant> findByDistanceLessThan(int distanceIsLessThan, Pageable pageable);
}
