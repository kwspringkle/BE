package com.ITSS.ITSS_NIHONGO.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "min_price", nullable = true)
    private int minPrice;

    @Column(name = "max_price", nullable = true)
    private int maxPrice;

    @Column(name = "distance", nullable = true)
    private int distance;

    @Column(name = "image_url", nullable = true)
    private  String imageUrl;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "opentime", nullable = true)
    private String openTime;

    @Column(name = "closetime", nullable = true)
    private String closeTime;
}
