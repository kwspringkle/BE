package com.ITSS.ITSS_NIHONGO.controller;

import com.ITSS.ITSS_NIHONGO.dto.response.Dishes.DishResponse;
import com.ITSS.ITSS_NIHONGO.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @GetMapping("/disharmonious")
    public ResponseEntity<Map<String, Object>> getDishFamousList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DishResponse> dishFamousList = dishService.getDishfamousList();
            if(dishFamousList.isEmpty()){
                response.put("status", "fail");
                response.put("message", "No famous dishes found");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", dishFamousList);
                return ResponseEntity.ok(response);
            }
        }catch (Exception e){
            response.put("status", "error");
            response.put("message", "An error occurred while fetching famous dishes");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/disharmonious-all")
    public ResponseEntity<Map<String, Object>> getAllDishFamousList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DishResponse> dishFamousList = dishService.getAllDishfamousList();
            if (dishFamousList.isEmpty()) {
                response.put("status", "fail");
                response.put("message", "No famous dishes found");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", dishFamousList);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching famous dishes");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/dishWithSameIngredients")
    public ResponseEntity<Map<String, Object>> getDishWithSameIngredients(@RequestParam int dishId,
                                                                          @RequestParam int restaurantId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DishResponse> dishList = dishService.findDishWithSameIngredients(dishId,restaurantId);
            if (dishList.isEmpty()) {
                response.put("status", "fail");
                response.put("message", "No dishes found with same ingredients");
                return ResponseEntity.status(404).body(response);
            } else {
                response.put("status", "success");
                response.put("data", dishList);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while fetching dishes with same ingredients");
            return ResponseEntity.status(500).body(response);
        }
    }
}
