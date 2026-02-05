package com.cda.food.dtos;

import com.cda.food.entities.Ingredient.IngredientType;

public record IngredientRequestDTO (
    String libelle,
    IngredientType type,
    Integer calorieCount
) {}
