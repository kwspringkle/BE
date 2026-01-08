package com.ITSS.ITSS_NIHONGO.service;

import com.ITSS.ITSS_NIHONGO.Iservice.IRestaurant;
import com.ITSS.ITSS_NIHONGO.dto.response.Restaurant.RestaurantResponse;
import com.ITSS.ITSS_NIHONGO.model.Restaurant;
import com.ITSS.ITSS_NIHONGO.model.RestaurantReview;
import com.ITSS.ITSS_NIHONGO.repository.RestaurantRepository;
import com.ITSS.ITSS_NIHONGO.repository.RestaurantReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurant {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    @Override
    public Page<RestaurantResponse> getRestaurantrecentlyList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Restaurant> restaurants = restaurantRepository.findByDistanceLessThan(2000, pageable);
        return restaurants.map(restaurant -> RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .imageUrl(restaurant.getImageUrl())
                .distance(restaurant.getDistance())
                .minprice(restaurant.getMinPrice())
                .maxprice(restaurant.getMaxPrice())
                .build());
    }

    @Override
    public RestaurantResponse getRestaurantDetail(int id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant == null) {
            return null;
        }

        List<RestaurantReview> reviews = restaurantReviewRepository.findByRestaurant_Id(id);
        float rate = 0;

        // Tính rate trung bình
        if (!reviews.isEmpty()) {
            float totalRate = 0;
            for (RestaurantReview review : reviews) {
                totalRate += review.getRating();
            }
            rate = totalRate / reviews.size();
        }

        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .distance(restaurant.getDistance())
                .minprice(restaurant.getMinPrice())
                .maxprice(restaurant.getMaxPrice())
                .imageUrl(restaurant.getImageUrl())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .description(restaurant.getDescription())
                .rate(rate)
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .build();
    }

}
