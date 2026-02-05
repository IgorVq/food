package com.cda.food.dtos;

import com.cda.food.entities.RecipeIngredient.QuantityType;

public record IngredientWithQuantityDTO (
    Integer ingredientId,
    String libelle,
    QuantityType quantityType,
    Integer quantity
) {}
