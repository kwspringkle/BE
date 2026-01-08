package com.ITSS.ITSS_NIHONGO.service;

import com.ITSS.ITSS_NIHONGO.Iservice.IDishRestaurant;
import com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant.DishRestaurantAll;
import com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant.DishRestaurantDetail;
import com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant.RestaurantByDish;
import com.ITSS.ITSS_NIHONGO.dto.response.DishRestaurant.SearchResult;
import com.ITSS.ITSS_NIHONGO.dto.response.Dishes.DishResponse;
import com.ITSS.ITSS_NIHONGO.model.DishRestaurant;
import com.ITSS.ITSS_NIHONGO.model.DishReview;
import com.ITSS.ITSS_NIHONGO.repository.DishRestaurantRepository;
import com.ITSS.ITSS_NIHONGO.repository.DishReviewRepository;
import com.ITSS.ITSS_NIHONGO.repository.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishRestaurantService implements IDishRestaurant {

    @Autowired
    private DishRestaurantRepository dishRestaurantRepository;
    @Autowired
    private DishReviewRepository dishReviewRepository;
    @Autowired
    private FavouriteRepository favouriteRepository;

    @Override
    public Page<DishRestaurantAll> getDishRestaurantAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DishRestaurant> dishRestaurantAllList = dishRestaurantRepository.findAll(pageable);
        if (dishRestaurantAllList.isEmpty()) {
            return null;
        }
        return dishRestaurantAllList.map(dr -> DishRestaurantAll.builder()
                .id(dr.getId())
                .dishesname(dr.getDish().getName())
                .restaurantname(dr.getRestaurant().getName())
                .imageUrl(dr.getDish().getImageUrl())
                .distance(dr.getRestaurant().getDistance())
                .build());
    }

    @Override
    public List<DishResponse> getDishRestaurantList(int restaurantId) {
        List<DishRestaurant> dishRestaurants = dishRestaurantRepository.findByRestaurant_Id(restaurantId);
        if (dishRestaurants.isEmpty()) {
            return null;
        }
        return dishRestaurants.stream().map(dr -> {
            float rate = 0;
           List<DishReview> reviews = dishReviewRepository.findByDish_Id(dr.getDish().getId());
            if (!reviews.isEmpty()) {
                float totalRate = 0;
                for (DishReview review : reviews) {
                    totalRate += review.getRate();
                }
                rate = totalRate / reviews.size();
            }
           if(rate >= 4.0){
               return DishResponse.builder()
                       .id(dr.getDish().getId())
                       .name(dr.getDish().getName())
                       .imageUrl(dr.getDish().getImageUrl())
                       .build();
           }
            return null;
        }).toList();
    }

    @Override
    public DishRestaurantDetail getDishRestaurantDetail(int id) {
        DishRestaurant dishRestaurant = dishRestaurantRepository.findById(id).orElse(null);
        if (dishRestaurant == null) {
            return null;
        }
        Long countLike = favouriteRepository.countByDish_Id(dishRestaurant.getDish().getId());
        int likes = countLike != null ? countLike.intValue() : 0;

        DishRestaurantDetail detail = new DishRestaurantDetail();
        detail.id = dishRestaurant.getId();
        detail.dishId = dishRestaurant.getDish().getId();
        detail.restaurantId = dishRestaurant.getRestaurant().getId();
        detail.dishesname = dishRestaurant.getDish().getName();
        detail.restaurantname = dishRestaurant.getRestaurant().getName();
        detail.distance = dishRestaurant.getRestaurant().getDistance();
        detail.imageUrlDish = dishRestaurant.getDish().getImageUrl();
        detail.imageUrlRestaurant = dishRestaurant.getRestaurant().getImageUrl();
        detail.price = dishRestaurant.getPrice();
        detail.description = dishRestaurant.getDish().getDescription();
        detail.ingredients = dishRestaurant.getDish().getIngredients();
        detail.countLike = likes;
        return detail;
    }

    @Override
    public DishRestaurantDetail getDishRestaurantDetail(int dishId, int restaurantId) {
        DishRestaurant dishRestaurant = dishRestaurantRepository.findByDishIdAndRestaurantId(dishId,restaurantId).orElse(null);
        if (dishRestaurant == null) {
            return null;
        }
        Long countLike = favouriteRepository.countByDish_Id(dishRestaurant.getDish().getId());
        int likes = countLike != null ? countLike.intValue() : 0;

        DishRestaurantDetail detail = new DishRestaurantDetail();
        detail.id = dishRestaurant.getId();
        detail.dishId = dishRestaurant.getDish().getId();
        detail.restaurantId = dishRestaurant.getRestaurant().getId();
        detail.dishesname = dishRestaurant.getDish().getName();
        detail.restaurantname = dishRestaurant.getRestaurant().getName();
        detail.distance = dishRestaurant.getRestaurant().getDistance();
        detail.imageUrlDish = dishRestaurant.getDish().getImageUrl();
        detail.imageUrlRestaurant = dishRestaurant.getRestaurant().getImageUrl();
        detail.price = dishRestaurant.getPrice();
        detail.description = dishRestaurant.getDish().getDescription();
        detail.ingredients = dishRestaurant.getDish().getIngredients();
        detail.countLike = likes;
        return detail;
    }

    @Override
    public List<RestaurantByDish> getRestaurantByDish(int dishId, int dishRestaurantId) {
        List<DishRestaurant> dishRestaurants = dishRestaurantRepository.findByDish_Id(dishId);
        if (dishRestaurants.isEmpty()) {
            return null;
        }
        return dishRestaurants.stream().map(dr -> {
            if(dr.getId() != dishRestaurantId) {
                return RestaurantByDish.builder()
                        .restaurantName(dr.getRestaurant().getName())
                        .imageUrl(dr.getRestaurant().getImageUrl())
                        .restaurantId(dr.getRestaurant().getId())
                        .build();
            }
            return null;
        }).toList();
    }

    @Override
    public Page<SearchResult> searchDishOrRestaurant(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DishRestaurant> results = dishRestaurantRepository.searchByKeyword(keyword, pageable);

        if (results.isEmpty()) {
            return Page.empty();
        }

        return results.map(dr -> {
            // Tính rating trung bình
            float rating = 0;
            List<DishReview> reviews = dishReviewRepository.findByDish_Id(dr.getDish().getId());
            if (!reviews.isEmpty()) {
                float totalRate = 0;
                for (DishReview review : reviews) {
                    totalRate += review.getRate();
                }
                rating = totalRate / reviews.size();
            }

            return SearchResult.builder()
                    .dishRestaurantId(dr.getId())
                    .dishName(dr.getDish().getName())
                    .restaurantName(dr.getRestaurant().getName())
                    .price(dr.getPrice())
                    .rating(rating)
                    .imageUrl(dr.getDish().getImageUrl())
                    .build();
        });
    }
}
