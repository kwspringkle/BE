package com.ITSS.ITSS_NIHONGO.service;

import com.ITSS.ITSS_NIHONGO.Iservice.IDishReviewServer;
import com.ITSS.ITSS_NIHONGO.dto.request.DishReview.AddDishReview;
import com.ITSS.ITSS_NIHONGO.dto.request.DishReview.UpdateDishReview;
import com.ITSS.ITSS_NIHONGO.dto.response.DishReview.DishReviewByDish;
import com.ITSS.ITSS_NIHONGO.model.DishReview;
import com.ITSS.ITSS_NIHONGO.model.Dishes;
import com.ITSS.ITSS_NIHONGO.model.Users;
import com.ITSS.ITSS_NIHONGO.repository.DishRepository;
import com.ITSS.ITSS_NIHONGO.repository.DishReviewRepository;
import com.ITSS.ITSS_NIHONGO.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishReviewService implements IDishReviewServer {
    @Autowired
    private DishReviewRepository dishReviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DishRepository dishRepository;

    @Override
    public boolean addDishReview(int userId, AddDishReview addDishReview) {
        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        Dishes dish = dishRepository.findById(addDishReview.dishId).orElse(null);
        if (dish == null) {
            return false;
        }

        DishReview dishReview = DishReview.builder()
                .user(user)
                .dish(dish)
                .rate(addDishReview.rating)
                .comment(addDishReview.comment)
                .createdAt(LocalDateTime.now())
                .build();
        dishReviewRepository.save(dishReview);
        return true;
    }

    @Override
    public boolean updateDishReview(UpdateDishReview updateDishReview) {
        DishReview dishReview = dishReviewRepository.findById(updateDishReview.id).orElse(null);
        if (dishReview == null) {
            return false;
        }
        dishReview.setComment(updateDishReview.comment);
        dishReviewRepository.save(dishReview);
        return true;
    }

    @Override
    public boolean deleteDishReview(int dishReviewId) {
        DishReview dishReview = dishReviewRepository.findById(dishReviewId).orElse(null);
        if (dishReview == null) {
            return false;
        }
        dishReviewRepository.delete(dishReview);
        return true;
    }

    @Override
    public List<DishReviewByDish> getDishReviewByDish(int dishId) {
        List<DishReview> dishReviews = dishReviewRepository.findByDish_Id(dishId);
        if (dishReviews.isEmpty()) {
            return null;
        }
        List<DishReviewByDish> dishReviewByDishes = new ArrayList<>();
        for (DishReview dishReview : dishReviews) {
            Users users = dishReview.getUser();
            Dishes dishes = dishReview.getDish();
            DishReviewByDish dishReviewByDish = DishReviewByDish.builder()
                    .dishReviewId(dishReview.getId())
                    .fullName(users.getName())
                    .dishesName(dishes.getName())
                    .comment(dishReview.getComment())
                    .avatar(users.getAvatar())
                    .rate(dishReview.getRate())
                    .date(dishReview.getCreatedAt())
                    .build();
            dishReviewByDishes.add(dishReviewByDish);
        }
        return dishReviewByDishes;
    }
}
