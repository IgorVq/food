package com.cda.food.dtos;

import com.cda.food.entities.Ingredient.IngredientType;

public record IngredientResponseDTO (
    Integer id,
    String libelle,
    IngredientType type,
    Integer calorieCount
) {}
