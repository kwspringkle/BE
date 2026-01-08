package com.ITSS.ITSS_NIHONGO.service;

import com.ITSS.ITSS_NIHONGO.Iservice.IFavourite;
import com.ITSS.ITSS_NIHONGO.dto.request.Favourite.AddFavourite;
import com.ITSS.ITSS_NIHONGO.dto.request.Favourite.DeleteFavorite;
import com.ITSS.ITSS_NIHONGO.dto.response.Favourite.FavouriteResponse;
import com.ITSS.ITSS_NIHONGO.model.Dishes;
import com.ITSS.ITSS_NIHONGO.model.Favourite;
import com.ITSS.ITSS_NIHONGO.model.Restaurant;
import com.ITSS.ITSS_NIHONGO.model.Users;
import com.ITSS.ITSS_NIHONGO.repository.DishRepository;
import com.ITSS.ITSS_NIHONGO.repository.FavouriteRepository;
import com.ITSS.ITSS_NIHONGO.repository.RestaurantRepository;
import com.ITSS.ITSS_NIHONGO.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService implements IFavourite {
    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;

    @Override
    public List<FavouriteResponse> get3Favourite(int userId) {
        List<Favourite> favourites = favouriteRepository.findTop3ByUserIdOrderByCreatedAtDesc(userId);
        if (favourites.isEmpty()) {
            return null;
        }
        return favourites.stream()
                .map(favouriteItem -> {
                    FavouriteResponse.FavouriteResponseBuilder builder = FavouriteResponse.builder()
                            .id(favouriteItem.getId());

                    if (favouriteItem.getDish() != null) {
                        builder.dishId(favouriteItem.getDish().getId())
                               .dishesname(favouriteItem.getDish().getName())
                               .imageUrl(favouriteItem.getDish().getImageUrl());
                    } if (favouriteItem.getRestaurant() != null) {
                        builder.restaurantName(favouriteItem.getRestaurant().getName());
                    }

                    return builder.build();
                })
                .toList();
    }

    @Override
    public List<FavouriteResponse> getAllFavourite(int userId) {
        List<Favourite> favourites = favouriteRepository.findByUser_IdOrderByDish_RateDesc(userId);
        if (favourites.isEmpty()) {
            return null;
        }
        return favourites.stream()
                .filter(favouriteItem -> favouriteItem.getDish() != null)
                .map(favouriteItem -> {
                    Long likesCount = favouriteRepository.countByDish_Id(favouriteItem.getDish().getId());
                    int likes = likesCount != null ? likesCount.intValue() : 0;
                    return FavouriteResponse.builder()
                            .id(favouriteItem.getId())
                            .dishId(favouriteItem.getDish().getId())
                            .dishesname(favouriteItem.getDish().getName())
                            .imageUrl(favouriteItem.getDish().getImageUrl())
                            .likes(likes)
                            .description(favouriteItem.getDish().getDescription())
                            .build();
                })
                .toList();
    }

    @Override
    public boolean addFavourite(int userId, AddFavourite addFavourite) {
        // Kiểm tra favourite đã tồn tại
        Favourite favourite = favouriteRepository.findByUser_IdAndDish_Id(userId, addFavourite.dishId);
        if (favourite != null) {
            return false;
        }

        // Validate user
        Users users = userRepository.findById(userId).orElse(null);
        if (users == null) {
            return false;
        }

        // Validate dish
        Dishes dishes = dishRepository.findById(addFavourite.dishId).orElse(null);
        if (dishes == null) {
            return false;
        }

        // Xử lý restaurant (có thể null)
        Restaurant restaurant = null;
        if (addFavourite.restaurantId != null && addFavourite.restaurantId > 0) {
            restaurant = restaurantRepository.findById(addFavourite.restaurantId).orElse(null);
        }

        // Tạo Favourite object MỘT LẦN (bên ngoài if-else)
        Favourite newFavourite = Favourite.builder()
                .user(users)
                .dish(dishes)
                .restaurant(restaurant) // null hoặc có giá trị đều được
                .createdAt(java.time.LocalDateTime.now())
                .build();

        favouriteRepository.save(newFavourite);
        return true;
    }

    @Override
    public boolean deleteFavourite(int userId,DeleteFavorite deleteFavorite) {
        Favourite favourite = null; // Cần thêm userId vào DeleteFavorite

        try {
            // Trường hợp 1: favoriteId không rỗng
            if (deleteFavorite.favoriteId != null && deleteFavorite.favoriteId > 0) {
                favourite = favouriteRepository.findById(deleteFavorite.favoriteId).orElse(null);
            }
            // Trường hợp 3: dishId và restaurantId không rỗng
            else if (deleteFavorite.dishId != null && deleteFavorite.dishId > 0
                    && deleteFavorite.restaurantId != null && deleteFavorite.restaurantId > 0) {
                favourite = favouriteRepository.findByUserIdAndDishIdAndRestaurantId(
                    userId, deleteFavorite.dishId, deleteFavorite.restaurantId
                ).orElse(null);
            }
            // Trường hợp 2: chỉ dishId không rỗng
            else if (deleteFavorite.dishId != null && deleteFavorite.dishId > 0) {
                favourite = favouriteRepository.findByUserIdAndDishIdAndRestaurantIsNull(
                    userId, deleteFavorite.dishId
                ).orElse(null);
            }

            if (favourite == null) {
                return false;
            }

            favouriteRepository.delete(favourite);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
