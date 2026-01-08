package com.ITSS.ITSS_NIHONGO.controller;

import com.ITSS.ITSS_NIHONGO.dto.response.Restaurant.RestaurantResponse;
import com.ITSS.ITSS_NIHONGO.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/restaurant-recently")
    public ResponseEntity<Map<String, Object>> getRestaurantRecently(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size) {
        Map<String, Object> map = new HashMap<>();
        try {
            Page<RestaurantResponse> restaurantPage = restaurantService.getRestaurantrecentlyList(page, size);

            if (restaurantPage.isEmpty()) {
                map.put("status", "fail");
                map.put("message", "No nearby restaurants found");
                return ResponseEntity.status(404).body(map);
            } else {
                map.put("status", "success");
                map.put("data", restaurantPage.getContent());
                map.put("currentPage", restaurantPage.getNumber() + 1);
                map.put("totalItems", restaurantPage.getTotalElements());
                map.put("totalPages", restaurantPage.getTotalPages());
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while fetching nearby restaurants");
            return ResponseEntity.status(500).body(map);
        }
    }

    @GetMapping("/restaurant-detail")
    public ResponseEntity<Map<String, Object>> getRestaurantDetail(@RequestParam int id) {
        Map<String, Object> map = new HashMap<>();
        try {
            RestaurantResponse restaurantDetail = restaurantService.getRestaurantDetail(id);
            if (restaurantDetail == null) {
                map.put("status", "fail");
                map.put("message", "Restaurant not found");
                return ResponseEntity.status(404).body(map);
            } else {
                map.put("status", "success");
                map.put("data", restaurantDetail);
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while fetching restaurant details");
            return ResponseEntity.status(500).body(map);
        }
    }
}
