package com.ITSS.ITSS_NIHONGO.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_reviews")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "rating", nullable = true)
    private float rating;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;
}
