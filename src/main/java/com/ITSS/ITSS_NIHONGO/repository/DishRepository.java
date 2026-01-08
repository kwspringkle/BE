package com.ITSS.ITSS_NIHONGO.repository;

import com.ITSS.ITSS_NIHONGO.model.Dishes;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dishes, Integer> {
    List<Dishes> findTop3ByOrderByRateDesc(PageRequest of);

    List<Dishes> findAllByOrderByRateDesc();
}
