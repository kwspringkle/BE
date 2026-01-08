package com.ITSS.ITSS_NIHONGO.service;

import com.ITSS.ITSS_NIHONGO.Iservice.IDishesService;
import com.ITSS.ITSS_NIHONGO.dto.response.Dishes.DishResponse;
import com.ITSS.ITSS_NIHONGO.model.DishRestaurant;
import com.ITSS.ITSS_NIHONGO.model.Dishes;
import com.ITSS.ITSS_NIHONGO.repository.DishRepository;
import com.ITSS.ITSS_NIHONGO.repository.DishRestaurantRepository;
import com.ITSS.ITSS_NIHONGO.repository.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService implements IDishesService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    public DishRestaurantRepository dishRestaurantRepository;

    @Override
    public List<DishResponse> getDishfamousList() {
        List<Dishes> dishes = dishRepository.findAll();
        if (dishes.isEmpty()) {
            return null;
        }
        return dishes.stream()
                .map(dish -> {
                    Long countLike = favouriteRepository.countByDish_Id(dish.getId());
                    int likes = countLike != null ? countLike.intValue() : 0;
                    return DishResponse.builder()
                            .id(dish.getId())
                            .name(dish.getName())
                            .imageUrl(dish.getImageUrl())
                            .likes(likes)
                            .description(dish.getDescription())
                            .build();
                })
                .sorted((d1, d2) -> Integer.compare(d2.getLikes(), d1.getLikes()))
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponse> getAllDishfamousList() {
        List<Dishes> dishes = dishRepository.findAllByOrderByRateDesc();
        if (dishes.isEmpty()) {
            return null;
        }
        return dishes.stream().map(dish ->{
                            Long countLike = favouriteRepository.countByDish_Id(dish.getId());
                            int likes = countLike != null ? countLike.intValue() : 0;
                            return DishResponse.builder()
                                    .id(dish.getId())
                                    .name(dish.getName())
                                    .imageUrl(dish.getImageUrl())
                                    .likes(likes)
                                    .description(dish.getDescription())
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponse> findDishWithSameIngredients(int dishId, int restaurantId) {
        Optional<Dishes> dish = dishRepository.findById(dishId);
        if(dish.isEmpty()){
            return null;
        }

        String ingredientsStr = dish.get().getIngredients();
        if(ingredientsStr == null || ingredientsStr.trim().isEmpty()){
            return null;
        }

        List<String> targetIngredients = java.util.stream.Stream.of(ingredientsStr.split("[、,]")) // Tách cả 、 và ,
                .map(String::trim)
                .map(s -> s.replaceAll("[。]", "")) // Loại bỏ dấu chấm cuối
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .toList();

        if(targetIngredients.isEmpty()){
            return null;
        }

        System.out.println("Target ingredients: " + targetIngredients);

        List<Dishes> allDishes = dishRepository.findAll();

        return allDishes.stream()
                .filter(d -> d.getId() != dishId)
                .filter(d -> d.getIngredients() != null && !d.getIngredients().trim().isEmpty())
                .map(d -> {
                    List<String> currentIngredients = java.util.stream.Stream.of(d.getIngredients().split("[、,]"))
                            .map(String::trim)
                            .map(s -> s.replaceAll("[。]", ""))
                            .map(String::toLowerCase)
                            .filter(s -> !s.isEmpty())
                            .toList();

                    System.out.println("Comparing with dish " + d.getId() + ": " + currentIngredients);

                    long matchCount = targetIngredients.stream()
                            .filter(currentIngredients::contains)
                            .count();

                    System.out.println("Match count: " + matchCount);

                    if(matchCount > 0){
                        Optional<DishRestaurant> dishRestaurant = dishRestaurantRepository
                                .findByDish_IdAndRestaurant_Id(d.getId(), restaurantId);
                        if(dishRestaurant.isPresent()){
                            return DishResponse.builder()
                                    .id(d.getId())
                                    .name(d.getName())
                                    .imageUrl(d.getImageUrl())
                                    .build();
                        }
                    }
                    return null;
                })
                .filter(java.util.Objects::nonNull)
                .toList();
    }
}
