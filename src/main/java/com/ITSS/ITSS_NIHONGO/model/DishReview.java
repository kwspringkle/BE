package com.ITSS.ITSS_NIHONGO.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "dish_reviews")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dishes dish;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "rating", nullable = true)
    private float rate;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
