package com.cda.food.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cda.food.dtos.RecipeRequestDTO;
import com.cda.food.dtos.RecipeResponseDTO;
import com.cda.food.entities.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMappers {
    @Mapping(target = "userId", source = "user.id")
    RecipeResponseDTO toDto(Recipe recipe);
    List<RecipeResponseDTO> toDtoList(List<Recipe> recipe);
    
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Recipe toEntity(RecipeRequestDTO recipeRequestDTO);    
}
