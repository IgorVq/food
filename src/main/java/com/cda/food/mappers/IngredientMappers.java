package com.cda.food.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cda.food.dtos.IngredientRequestDTO;
import com.cda.food.dtos.IngredientResponseDTO;
import com.cda.food.entities.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientMappers {
    @Mapping(target = "userId", source = "user.id")
    IngredientResponseDTO toDto(Ingredient ingredient);
    List<IngredientResponseDTO> toDtoList(List<Ingredient> ingredients);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Ingredient toEntity(IngredientRequestDTO ingredientRequestDTO);
}
