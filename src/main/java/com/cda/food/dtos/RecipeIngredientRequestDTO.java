package com.cda.food.dtos;

import com.cda.food.entities.RecipeIngredient.QuantityType;

public record RecipeIngredientRequestDTO (
    Integer recipeId,
    Integer ingredientId,
    Integer quantity,
    QuantityType quantityType
) {}
