package com.ITSS.ITSS_NIHONGO.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dishes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dishes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "ingredients", nullable = true)
    private String ingredients;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "rate" , nullable = false)
    private float rate;
}
