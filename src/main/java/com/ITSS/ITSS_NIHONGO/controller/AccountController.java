package com.ITSS.ITSS_NIHONGO.controller;


import com.ITSS.ITSS_NIHONGO.config.JwtService;
import com.ITSS.ITSS_NIHONGO.dto.request.User.LoginRequest;
import com.ITSS.ITSS_NIHONGO.dto.request.User.RegisterRequest;
import com.ITSS.ITSS_NIHONGO.dto.request.User.UpdatePassword;
import com.ITSS.ITSS_NIHONGO.dto.request.User.UpdateProfile;
import com.ITSS.ITSS_NIHONGO.repository.UserRepository;
import com.ITSS.ITSS_NIHONGO.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AccountController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginRequest body) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.username, body.password));

            String username = authentication.getName();
            String token = jwtService.generateToken(username);

            Map<String, Object> response = Map.of(
                    "token", token,
                    "message", "Login successful"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtService.extractUserId(token);
        try {
            var user = userService.getUserProfile(userId);
            if (user != null) {
                response.put("data", user);
            } else {
                response.put("message", "User not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Map<String,Object>> register(@RequestBody RegisterRequest body) {
        Map<String, Object> response = new HashMap<>();
        try{
            boolean check = userService.registerUser(body.name, body.password, body.username, body.national);
            if (check) {
                response.put("message", "Registration successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Username already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Map<String,Object>> updatePassword(HttpServletRequest request ,@RequestBody UpdatePassword updatePassword) {
        Map<String, Object> response = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtService.extractUserId(token);
        try {
            boolean isUpdated = userService.updatePassword(userId, updatePassword);
            if (isUpdated) {
                response.put("message", "Password updated successfully");
            } else {
                response.put("message", "Password update failed");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateUser" )
    public ResponseEntity<Map<String,Object>> updateUser(HttpServletRequest request ,@RequestBody UpdateProfile updateUser) {
        Map<String, Object> response = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtService.extractUserId(token);
        try {
            boolean isUpdated = userService.updateUserInfo(userId, updateUser);
            if (isUpdated) {
                response.put("message", "User information updated successfully");
            } else {
                response.put("message", "User information update failed");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
