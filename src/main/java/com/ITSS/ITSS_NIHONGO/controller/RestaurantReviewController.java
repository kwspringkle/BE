package com.ITSS.ITSS_NIHONGO.controller;

import com.ITSS.ITSS_NIHONGO.config.JwtService;
import com.ITSS.ITSS_NIHONGO.dto.request.RestaurantReview.AddRestaurantReview;
import com.ITSS.ITSS_NIHONGO.dto.request.RestaurantReview.UpdateRestaurantReview;
import com.ITSS.ITSS_NIHONGO.service.RestaurantReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RestaurantReviewController {

    private final JwtService jwtService;
    private final RestaurantReviewService restaurantReviewService;

    @PostMapping("/restaurantReview")
    public ResponseEntity<Map<String,Object>> saveRestaurantReview(HttpServletRequest request,
                                                                    @RequestBody AddRestaurantReview addRestaurantReview){
        Map<String,Object> map = new HashMap<>();
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                map.put("status", "error");
                map.put("message", "Missing or invalid Authorization header");
                return ResponseEntity.status(401).body(map);
            }

            String token = authHeader.substring(7);
            int userId = jwtService.extractUserId(token);

            System.out.println("Attempting to add review - UserId: " + userId + ", RestaurantId: " + addRestaurantReview.restaurantId);

            boolean addComment = restaurantReviewService.addRestaurantReview(userId, addRestaurantReview);
            if(addComment){
                map.put("status", "success");
                map.put("message", "Review added successfully");
            } else {
                map.put("status", "fail");
                map.put("message", "Failed to add review - User or Restaurant not found");
            }
            return ResponseEntity.ok(map);
        }catch (Exception e){
            map.put("status", "error");
            map.put("message", "An error occurred while adding the review: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(map);
        }
    }

    @PutMapping("/restaurantReview")
    public ResponseEntity<Map<String,Object>> updateRestaurantReview(HttpServletRequest request,
                                                                    @RequestBody UpdateRestaurantReview updateRestaurantReview){
        Map<String,Object> map = new HashMap<>();
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                map.put("status", "error");
                map.put("message", "Missing or invalid Authorization header");
                return ResponseEntity.status(401).body(map);
            }

            int userId = jwtService.extractUserId(authHeader.substring(7));
            boolean updateComment = restaurantReviewService.updateRestaurantReview(userId, updateRestaurantReview);
            if(updateComment){
                map.put("status", "success");
                map.put("message", "Review updated successfully");
            } else {
                map.put("status", "fail");
                map.put("message", "Failed to update review");
            }
            return ResponseEntity.ok(map);
        }catch (Exception e){
            map.put("status", "error");
            map.put("message", "An error occurred while updating the review");
            return ResponseEntity.status(500).body(map);
        }
    }

    @GetMapping("/restaurantReview")
    public ResponseEntity<Map<String,Object>> getAllRestaurantReviews(@RequestParam int restaurantId){
        Map<String,Object> map = new HashMap<>();
        try {
            var reviews = restaurantReviewService.getRestaurantReviewByRestaurant(restaurantId);
            if (reviews.isEmpty()) {
                map.put("status", "fail");
                map.put("message", "No reviews found for the specified restaurant");
                return ResponseEntity.status(404).body(map);
            } else {
                map.put("status", "success");
                map.put("data", reviews);
            }
            return ResponseEntity.ok(map);
        }catch (Exception e){
            map.put("status", "error");
            map.put("message", "An error occurred while retrieving reviews");
            return ResponseEntity.status(500).body(map);
        }
    }

    @DeleteMapping("/restaurantReview")
    public ResponseEntity<Map<String,Object>> deleteRestaurantReview(HttpServletRequest request,
                                                                    @RequestParam int restaurantReviewId) {
        Map<String, Object> map = new HashMap<>();
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                map.put("status", "error");
                map.put("message", "Missing or invalid Authorization header");
                return ResponseEntity.status(401).body(map);
            }

            int userId = jwtService.extractUserId(authHeader.substring(7));
            boolean deleteComment = restaurantReviewService.deleteRestaurantReview(userId, restaurantReviewId);
            if (deleteComment) {
                map.put("status", "success");
                map.put("message", "Review deleted successfully");
            } else {
                map.put("status", "fail");
                map.put("message", "Failed to delete review");
            }
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while deleting the review");
            return ResponseEntity.status(500).body(map);
        }
    }
}
