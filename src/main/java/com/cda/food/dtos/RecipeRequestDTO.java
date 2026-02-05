package com.cda.food.dtos;

public record RecipeRequestDTO (
    String name,
    Integer preparationTime,
    Integer cookingTime,
    Boolean share,
    Integer userId
) {}
