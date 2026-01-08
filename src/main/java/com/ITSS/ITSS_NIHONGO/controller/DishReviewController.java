package com.ITSS.ITSS_NIHONGO.controller;

import com.ITSS.ITSS_NIHONGO.config.JwtService;
import com.ITSS.ITSS_NIHONGO.dto.request.DishReview.AddDishReview;
import com.ITSS.ITSS_NIHONGO.dto.request.DishReview.UpdateDishReview;
import com.ITSS.ITSS_NIHONGO.service.DishReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DishReviewController {

    private final JwtService jwtService;
    private final DishReviewService dishReviewService;

    @PostMapping("/dishReview")
    public ResponseEntity<Map<String,Object>> saveDishReview(HttpServletRequest request,
                                                             @RequestBody AddDishReview addDishReview){
        Map<String,Object> map = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        int userId = jwtService.extractUserId(token);
        try {
            boolean addComment = dishReviewService.addDishReview(userId, addDishReview);
            if(addComment){
                map.put("status", "success");
                map.put("message", "Review added successfully");
            } else {
                map.put("status", "fail");
                map.put("message", "Failed to add review");
            }
            return ResponseEntity.ok(map);
        }catch (Exception e){
            map.put("status", "error");
            map.put("message", "An error occurred while adding the review");
            return ResponseEntity.status(500).body(map);
        }
    }

    @PutMapping("/dishReview")
    public ResponseEntity<Map<String,Object>> updateDishReview(@RequestBody UpdateDishReview updateDishReview){
        Map<String,Object> map = new HashMap<>();
        try {
            boolean updateComment = dishReviewService.updateDishReview(updateDishReview);
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

    @GetMapping("/dishReview")
    public ResponseEntity<Map<String,Object>> getAllDishReviews(@RequestParam int dishId){
        Map<String,Object> map = new HashMap<>();
        try {
            var reviews = dishReviewService.getDishReviewByDish(dishId);
            if(reviews.isEmpty()){
                map.put("status", "fail");
                map.put("message", "No reviews found for the specified dish");
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

    @DeleteMapping("/dishReview")
    public ResponseEntity<Map<String,Object>> deleteDishReview(@RequestParam int dishReviewId) {
        Map<String, Object> map = new HashMap<>();
        try {
            boolean deleteComment = dishReviewService.deleteDishReview(dishReviewId);
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
