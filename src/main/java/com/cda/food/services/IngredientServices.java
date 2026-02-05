package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.food.dtos.IngredientRequestDTO;
import com.cda.food.dtos.IngredientResponseDTO;
import com.cda.food.entities.Ingredient;
import com.cda.food.mappers.IngredientMappers;
import com.cda.food.repositories.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServices {
    private final IngredientRepository ingredientRepository;
    private final IngredientMappers ingredientMappers;

    public List<IngredientResponseDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredientMappers.toDtoList(ingredients);
    }

    public IngredientResponseDTO getIngredientById(Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id).isPresent() 
            ? ingredientRepository.findById(id).get() : null;
        return ingredient != null ? ingredientMappers.toDto(ingredient) : null;
    }

    public void createIngredient(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = ingredientMappers.toEntity(ingredientRequestDTO);
        ingredientRepository.save(ingredient);
    }

    public void updateIngredient(Integer id, IngredientRequestDTO ingredientRequestDTO) {
        if (!ingredientRepository.findById(id).isPresent()) {
            return;
        }
        Ingredient ingredientToUpdate = ingredientMappers.toEntity(ingredientRequestDTO);
        ingredientToUpdate.setId(id);
        ingredientRepository.save(ingredientToUpdate);
    }

    public void deleteIngredient(Integer id) {
        ingredientRepository.deleteById(id);
    }
}
