package com.ITSS.ITSS_NIHONGO.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dish_restaurant")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dishes dish;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "price", nullable = false)
    private float price;
}
