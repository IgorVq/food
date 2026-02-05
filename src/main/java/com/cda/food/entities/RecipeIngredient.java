package com.cda.food.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID", nullable = false)
    private Ingredient ingredient;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "QUANTITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;

    public enum QuantityType {
        UNITE,
        G,
        CL
    }
}
