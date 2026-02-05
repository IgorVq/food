package com.cda.food.dtos;

import com.cda.food.entities.RecipeIngredient.QuantityType;

public record RecipeIngredientResponseDTO (
    Integer id,
    Integer recipeId,
    Integer ingredientId,
    Integer quantity,
    QuantityType quantityType
) {}
