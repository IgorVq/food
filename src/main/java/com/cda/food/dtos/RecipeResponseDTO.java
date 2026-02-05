package com.cda.food.dtos;

public record RecipeResponseDTO (
    Integer id,
    String name,
    Integer preparationTime,
    Integer cookingTime,
    Boolean share,
    Integer userId
) {}
