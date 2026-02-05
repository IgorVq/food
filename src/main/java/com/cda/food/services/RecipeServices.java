package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.food.dtos.RecipeRequestDTO;
import com.cda.food.dtos.RecipeResponseDTO;
import com.cda.food.entities.Recipe;
import com.cda.food.entities.User;
import com.cda.food.mappers.RecipeMappers;
import com.cda.food.repositories.RecipeRepository;
import com.cda.food.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeServices {
    private final RecipeRepository recipeRepository;
    private final RecipeMappers recipeMappers;
    private final UserRepository userRepository;

    public List<RecipeResponseDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipeMappers.toDtoList(recipes);
    }

    public RecipeResponseDTO getRecipeById(Integer id) {
        Recipe recipe = recipeRepository.findById(id).isPresent() 
            ? recipeRepository.findById(id).get() : null;
        return recipeMappers.toDto(recipe);
    }

    public void createRecipe(RecipeRequestDTO recipeRequestDTO) {
        if (!userRepository.findById(recipeRequestDTO.userId()).isPresent()) {
            return;
        }
        User user = userRepository.findById(recipeRequestDTO.userId()).get();
        Recipe recipe = recipeMappers.toEntity(recipeRequestDTO);
        recipe.setUser(user);
        recipeRepository.save(recipe);
    }

    public void updatedRecipe(Integer id, RecipeRequestDTO recipeRequestDTO) {
        if (!userRepository.findById(recipeRequestDTO.userId()).isPresent()) {
            return;
        }
        User user = userRepository.findById(recipeRequestDTO.userId()).get();
        Recipe recipeToUpdate = recipeMappers.toEntity(recipeRequestDTO);
        recipeToUpdate.setId(id);
        recipeToUpdate.setUser(user);
        recipeRepository.save(recipeToUpdate);
    }

    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
    }
}
