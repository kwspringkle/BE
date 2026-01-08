package com.ITSS.ITSS_NIHONGO.controller;

import com.ITSS.ITSS_NIHONGO.config.JwtService;
import com.ITSS.ITSS_NIHONGO.dto.request.Favourite.AddFavourite;
import com.ITSS.ITSS_NIHONGO.dto.request.Favourite.DeleteFavorite;
import com.ITSS.ITSS_NIHONGO.service.FavouriteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FavouriteController {

    private final JwtService jwtService;
    private final FavouriteService favouriteService;

    @GetMapping("/favourite-top3" )
    public ResponseEntity<Map<String, Object>> get3Favourite(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtService.extractUserId(token);
        try {
            var favouriteList = favouriteService.get3Favourite(userId);
            if (favouriteList == null || favouriteList.isEmpty()) {
                map.put("status", "fail");
                map.put("message", "No favourite items found");
                return ResponseEntity.status(404).body(map);
            } else {
                map.put("status", "success");
                map.put("data", favouriteList);
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while fetching favourite items");
            return ResponseEntity.status(500).body(map);
        }
    }

    @GetMapping("/favourites" )
    public ResponseEntity<Map<String, Object>> getAllFavourite(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtService.extractUserId(token);
        try {
            var favouriteList = favouriteService.getAllFavourite(userId);
            if (favouriteList == null || favouriteList.isEmpty()) {
                map.put("status", "fail");
                map.put("message", "No favourite items found");
                return ResponseEntity.status(404).body(map);
            } else {
                map.put("status", "success");
                map.put("data", favouriteList);
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while fetching favourite items");
            return ResponseEntity.status(500).body(map);
        }
    }

    @PostMapping("/favourite")
    public ResponseEntity<Map<String, Object>> addFavourite(HttpServletRequest request,
                                                            @RequestBody AddFavourite addFavourite) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        int userId = jwtService.extractUserId(token);
        try {
            boolean isAdded = favouriteService.addFavourite(userId, addFavourite);
            if (isAdded) {
                map.put("status", "success");
                map.put("message", "Favourite added successfully");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "fail");
                map.put("message", "Failed to add favourite (it may already exist)");
                return ResponseEntity.status(400).body(map);
            }
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while adding favourite");
            return ResponseEntity.status(500).body(map);
        }
    }

    @DeleteMapping("/favourite")
    public ResponseEntity<Map<String, Object>> deleteFavouriteById(HttpServletRequest request,
                                                                   @RequestBody DeleteFavorite deleteFavorite) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        int userId = jwtService.extractUserId(token);
        try {
            boolean isDeleted = favouriteService.deleteFavourite(userId,deleteFavorite);
            if (isDeleted) {
                map.put("status", "success");
                map.put("message", "Favourite deleted successfully");
                return ResponseEntity.ok(map);
            } else {
                map.put("status", "fail");
                map.put("message", "Failed to delete favourite (it may not exist)");
                return ResponseEntity.status(400).body(map);
            }
        } catch (Exception e) {
            map.put("status", "error");
            map.put("message", "An error occurred while deleting the favourite item");
            return ResponseEntity.status(500).body(map);
        }
    }
}
