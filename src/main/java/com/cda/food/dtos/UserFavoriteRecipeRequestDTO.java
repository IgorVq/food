package com.cda.food.dtos;

public record UserFavoriteRecipeRequestDTO (
    Integer userId,
    Integer recipeId
) {}
