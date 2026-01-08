package com.ITSS.ITSS_NIHONGO.controller;

import com.ITSS.ITSS_NIHONGO.service.DishRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DishRestaurantController {
    private final DishRestaurantService dishRestaurantService;
    @GetMapping("/dish-restaurant-all")
    public ResponseEntity<Map<String, Object>> getDishRestaurantAllList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size) {
        Map<String, Object> response = new HashMap<>();
        try {
            var dishRestaurantPage = dishRestaurantService.getDishRestaurantAllList(page, size);

            if (dishRestaurantPage == null || dishRestaurantPage.isEmpty()) {
                response.put("status", "fail");
                response.put("message", "No dish-restaurant associations found");
                return ResponseEntity.status(404).body(response);
            }

            response.put("status", "success");
            response.put("data", dishRestaurantPage.getContent());
            response.put("totalItems", dishRestaurantPage.getTotalElements());
            response.put("totalPages", dishRestaurantPage.getTotalPages());
            response.put("currentPage", dishRestaurantPage.getNumber());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching dish-restaurant associations");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/dish-restaurant" )
    public ResponseEntity<Map<String, Object>> getDishRestaurantList(@RequestParam int restaurantId) {
        Map<String, Object> response = new HashMap<>();
        try {
            var dishRestaurantList = dishRestaurantService.getDishRestaurantList(restaurantId);
            if (dishRestaurantList == null || dishRestaurantList.isEmpty()) {
                response.put("status", "fail");
                response.put("message", "No dishes found for the specified restaurant");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", dishRestaurantList);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching dishes for the restaurant");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/dish-restaurant-detail" )
    public ResponseEntity<Map<String, Object>> getDishRestaurantDetail(@RequestParam int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            var dishRestaurantDetail = dishRestaurantService.getDishRestaurantDetail(id);
            if (dishRestaurantDetail == null) {
                response.put("status", "fail");
                response.put("message", "Dish-Restaurant association not found");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", dishRestaurantDetail);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching dish-restaurant details");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/dish-restaurant-detail-2")
    public ResponseEntity<Map<String, Object>> getDishRestaurantDetail2(@RequestParam int dishId,
                                                                       @RequestParam int restaurantId) {
        Map<String, Object> response = new HashMap<>();
        try {
            var dishRestaurantDetail = dishRestaurantService.getDishRestaurantDetail(dishId, restaurantId);
            if (dishRestaurantDetail == null) {
                response.put("status", "fail");
                response.put("message", "Dish-Restaurant association not found");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", dishRestaurantDetail);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching dish-restaurant details");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/restaurant-by-dish" )
    public ResponseEntity<Map<String, Object>> getRestaurantByDish(@RequestParam int dishId,
                                                                   @RequestParam int dishRestaurantId) {
        Map<String, Object> response = new HashMap<>();
        try {
            var restaurantList = dishRestaurantService.getRestaurantByDish(dishId,dishRestaurantId);
            if (restaurantList == null || restaurantList.isEmpty()) {
                response.put("status", "fail");
                response.put("message", "No restaurants found for the specified dish");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", restaurantList);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching restaurants for the dish");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        try {
            var searchResults = dishRestaurantService.searchDishOrRestaurant(keyword, page, size);
            if (searchResults == null || searchResults.isEmpty()) {
                response.put("status", "fail");
                response.put("message", "No results found for keyword: " + keyword);
                return ResponseEntity.status(404).body(response);
            }
            response.put("status", "success");
            response.put("data", searchResults.getContent());
            response.put("totalItems", searchResults.getTotalElements());
            response.put("totalPages", searchResults.getTotalPages());
            response.put("currentPage", searchResults.getNumber());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while searching");
            return ResponseEntity.status(500).body(response);
        }
    }
}
