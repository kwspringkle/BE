package com.ITSS.ITSS_NIHONGO.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = true)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = true)
    private Dishes dish;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;
}
