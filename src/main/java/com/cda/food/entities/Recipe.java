package com.cda.food.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PREPARATION_TIME", nullable = false)
    private Integer preparationTime;

    @Column(name = "COOKING_TIME", nullable = false)
    private Integer cookingTime;

    @Column(name = "SHARE", nullable = false)
    private Boolean share;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}