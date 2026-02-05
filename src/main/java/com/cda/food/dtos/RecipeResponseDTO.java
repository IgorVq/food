package com.cda.food.dtos;

import java.util.List;

public record RecipeResponseDTO (
    Integer id,
    String name,
    Integer preparationTime,
    Integer cookingTime,
    Boolean share,
    Integer calorieCount,
    Integer userId,
    List<IngredientWithQuantityDTO> ingredients
) {}
