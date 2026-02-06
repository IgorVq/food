package com.cda.food.dtos;

public record UserFavoriteRecipeResponseDTO (
    Integer id,
    Integer userId,
    Integer recipeId
) {}
