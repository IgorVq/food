package com.cda.food.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cda.food.dtos.RecipeIngredientRequestDTO;
import com.cda.food.dtos.RecipeIngredientResponseDTO;
import com.cda.food.entities.RecipeIngredient;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMappers {
    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "ingredientId", source = "ingredient.id")
    RecipeIngredientResponseDTO toDto(RecipeIngredient recipeIngredient);
    List<RecipeIngredientResponseDTO> toDtoList(List<RecipeIngredient> recipeIngredients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    RecipeIngredient toEntity(RecipeIngredientRequestDTO recipeIngredientRequestDTO);
}
