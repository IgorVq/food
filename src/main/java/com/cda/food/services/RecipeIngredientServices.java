package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.food.dtos.RecipeIngredientRequestDTO;
import com.cda.food.dtos.RecipeIngredientResponseDTO;
import com.cda.food.entities.Ingredient;
import com.cda.food.entities.Recipe;
import com.cda.food.entities.RecipeIngredient;
import com.cda.food.mappers.RecipeIngredientMappers;
import com.cda.food.repositories.IngredientRepository;
import com.cda.food.repositories.RecipeIngredientRepository;
import com.cda.food.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeIngredientServices {
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMappers recipeIngredientMappers;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public List<RecipeIngredientResponseDTO> getAllRecipeIngredients() {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAll();
        return recipeIngredientMappers.toDtoList(recipeIngredients);
    }

    public RecipeIngredientResponseDTO getRecipeIngredientById(Integer id) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id).isPresent()
            ? recipeIngredientRepository.findById(id).get() : null;
        return recipeIngredient != null ? recipeIngredientMappers.toDto(recipeIngredient) : null;
    }

    public void createRecipeIngredient(RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        if (!recipeRepository.findById(recipeIngredientRequestDTO.recipeId()).isPresent()) {
            return;
        }
        if (!ingredientRepository.findById(recipeIngredientRequestDTO.ingredientId()).isPresent()) {
            return;
        }
        Recipe recipe = recipeRepository.findById(recipeIngredientRequestDTO.recipeId()).get();
        Ingredient ingredient = ingredientRepository.findById(recipeIngredientRequestDTO.ingredientId()).get();
        RecipeIngredient recipeIngredient = recipeIngredientMappers.toEntity(recipeIngredientRequestDTO);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredientRepository.save(recipeIngredient);
    }

    public void updateRecipeIngredient(Integer id, RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        if (!recipeIngredientRepository.findById(id).isPresent()) {
            return;
        }
        if (!recipeRepository.findById(recipeIngredientRequestDTO.recipeId()).isPresent()) {
            return;
        }
        if (!ingredientRepository.findById(recipeIngredientRequestDTO.ingredientId()).isPresent()) {
            return;
        }
        Recipe recipe = recipeRepository.findById(recipeIngredientRequestDTO.recipeId()).get();
        Ingredient ingredient = ingredientRepository.findById(recipeIngredientRequestDTO.ingredientId()).get();
        RecipeIngredient recipeIngredient = recipeIngredientMappers.toEntity(recipeIngredientRequestDTO);
        recipeIngredient.setId(id);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredientRepository.save(recipeIngredient);
    }

    public void deleteRecipeIngredient(Integer id) {
        recipeIngredientRepository.deleteById(id);
    }
}
