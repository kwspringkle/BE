package com.ITSS.ITSS_NIHONGO.service;

import com.ITSS.ITSS_NIHONGO.Iservice.IRestaurantReview;
import com.ITSS.ITSS_NIHONGO.dto.request.RestaurantReview.AddRestaurantReview;
import com.ITSS.ITSS_NIHONGO.dto.request.RestaurantReview.UpdateRestaurantReview;
import com.ITSS.ITSS_NIHONGO.dto.response.RestaurantReview.RestaurantReviewByRestaurant;
import com.ITSS.ITSS_NIHONGO.model.Restaurant;
import com.ITSS.ITSS_NIHONGO.model.RestaurantReview;
import com.ITSS.ITSS_NIHONGO.model.Users;
import com.ITSS.ITSS_NIHONGO.repository.RestaurantRepository;
import com.ITSS.ITSS_NIHONGO.repository.RestaurantReviewRepository;
import com.ITSS.ITSS_NIHONGO.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantReviewService implements IRestaurantReview {
    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public boolean addRestaurantReview(int userId, AddRestaurantReview addRestaurantReview) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("ERROR: User not found with ID: " + userId);
            return false;
        }
        Restaurant restaurant = restaurantRepository.findById(addRestaurantReview.restaurantId).orElse(null);
        if (restaurant == null) {
            System.out.println("ERROR: Restaurant not found with ID: " + addRestaurantReview.restaurantId);
            return false;
        }

        try {
            RestaurantReview restaurantReview = RestaurantReview.builder()
                    .user(user)
                    .restaurant(restaurant)
                    .rating(addRestaurantReview.rating)
                    .comment(addRestaurantReview.comment)
                    .createdAt(LocalDateTime.now())
                    .build();
            restaurantReviewRepository.save(restaurantReview);
            System.out.println("SUCCESS: Restaurant review added for restaurant ID: " + addRestaurantReview.restaurantId);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Failed to save restaurant review: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRestaurantReview(UpdateRestaurantReview updateRestaurantReview) {
        RestaurantReview restaurantReview = restaurantReviewRepository.findById(updateRestaurantReview.id).orElse(null);
        if (restaurantReview == null) {
            return false;
        }
        restaurantReview.setComment(updateRestaurantReview.comment);
        restaurantReviewRepository.save(restaurantReview);
        return true;
    }

    @Override
    public boolean deleteRestaurantReview(int restaurantReviewId) {
        RestaurantReview restaurantReview = restaurantReviewRepository.findById(restaurantReviewId).orElse(null);
        if (restaurantReview == null) {
            return false;
        }
        restaurantReviewRepository.delete(restaurantReview);
        return true;
    }

    @Override
    public List<RestaurantReviewByRestaurant> getRestaurantReviewByRestaurant(int restaurantId) {
        List<RestaurantReview> restaurantReviews = restaurantReviewRepository.findByRestaurant_Id(restaurantId);
        if (restaurantReviews.isEmpty()) {
            return null;
        }
        List<RestaurantReviewByRestaurant> restaurantReviewByRestaurants = new ArrayList<>();
        for (RestaurantReview restaurantReview : restaurantReviews) {
            Users users = restaurantReview.getUser();
            Restaurant restaurant = restaurantReview.getRestaurant();
            RestaurantReviewByRestaurant restaurantReviewByRestaurant = RestaurantReviewByRestaurant.builder()
                    .restaurantReviewId(restaurantReview.getId())
                    .fullName(users.getName())
                    .restaurantName(restaurant.getName())
                    .comment(restaurantReview.getComment())
                    .avatar(users.getAvatar())
                    .rating(restaurantReview.getRating())
                    .date(restaurantReview.getCreatedAt())
                    .build();
            restaurantReviewByRestaurants.add(restaurantReviewByRestaurant);
        }
        return restaurantReviewByRestaurants;
    }
}
