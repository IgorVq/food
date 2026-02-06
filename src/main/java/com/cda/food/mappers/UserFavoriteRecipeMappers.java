package com.cda.food.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cda.food.dtos.UserFavoriteRecipeRequestDTO;
import com.cda.food.dtos.UserFavoriteRecipeResponseDTO;
import com.cda.food.entities.UserFavoriteRecipe;

@Mapper(componentModel = "spring")
public interface UserFavoriteRecipeMappers {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "recipeId", source = "recipe.id")
    UserFavoriteRecipeResponseDTO toDto(UserFavoriteRecipe userFavoriteRecipe);
    List<UserFavoriteRecipeResponseDTO> toDtoList(List<UserFavoriteRecipe> userFavoriteRecipes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    UserFavoriteRecipe toEntity(UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO);
}
