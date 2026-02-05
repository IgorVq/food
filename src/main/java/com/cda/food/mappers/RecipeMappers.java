package com.cda.food.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cda.food.dtos.IngredientWithQuantityDTO;
import com.cda.food.dtos.RecipeRequestDTO;
import com.cda.food.dtos.RecipeResponseDTO;
import com.cda.food.entities.Recipe;
import com.cda.food.entities.RecipeIngredient;

@Mapper(componentModel = "spring")
public interface RecipeMappers {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "ingredients", source = "recipeIngredients")
    RecipeResponseDTO toDto(Recipe recipe);
    List<RecipeResponseDTO> toDtoList(List<Recipe> recipe);
    
    @Mapping(target = "ingredientId", source = "ingredient.id")
    @Mapping(target = "libelle", source = "ingredient.libelle")
    IngredientWithQuantityDTO toIngredientWithQuantityDto(RecipeIngredient recipeIngredient);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Recipe toEntity(RecipeRequestDTO recipeRequestDTO);
}
