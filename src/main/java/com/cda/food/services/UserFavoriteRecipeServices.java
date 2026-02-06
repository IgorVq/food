package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.food.dtos.UserFavoriteRecipeRequestDTO;
import com.cda.food.dtos.UserFavoriteRecipeResponseDTO;
import com.cda.food.entities.Recipe;
import com.cda.food.entities.User;
import com.cda.food.entities.UserFavoriteRecipe;
import com.cda.food.mappers.UserFavoriteRecipeMappers;
import com.cda.food.repositories.RecipeRepository;
import com.cda.food.repositories.UserFavoriteRecipeRepository;
import com.cda.food.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFavoriteRecipeServices {
    private final UserFavoriteRecipeRepository userFavoriteRecipeRepository;
    private final UserFavoriteRecipeMappers userFavoriteRecipeMappers;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public List<UserFavoriteRecipeResponseDTO> getAllUserFavoriteRecipes() {
        List<UserFavoriteRecipe> userFavoriteRecipes = userFavoriteRecipeRepository.findAll();
        return userFavoriteRecipeMappers.toDtoList(userFavoriteRecipes);
    }

    public UserFavoriteRecipeResponseDTO getUserFavoriteRecipeById(Integer id) {
        UserFavoriteRecipe userFavoriteRecipe = userFavoriteRecipeRepository.findById(id).isPresent()
            ? userFavoriteRecipeRepository.findById(id).get() : null;
        return userFavoriteRecipe != null ? userFavoriteRecipeMappers.toDto(userFavoriteRecipe) : null;
    }

    public void createUserFavoriteRecipe(UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO) {
        if (!userRepository.findById(userFavoriteRecipeRequestDTO.userId()).isPresent()) {
            return;
        }
        if (!recipeRepository.findById(userFavoriteRecipeRequestDTO.recipeId()).isPresent()) {
            return;
        }
        User user = userRepository.findById(userFavoriteRecipeRequestDTO.userId()).get();
        Recipe recipe = recipeRepository.findById(userFavoriteRecipeRequestDTO.recipeId()).get();
        UserFavoriteRecipe userFavoriteRecipe = userFavoriteRecipeMappers.toEntity(userFavoriteRecipeRequestDTO);
        userFavoriteRecipe.setUser(user);
        userFavoriteRecipe.setRecipe(recipe);
        userFavoriteRecipeRepository.save(userFavoriteRecipe);
    }

    public void updateUserFavoriteRecipe(Integer id, UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO) {
        if (!userFavoriteRecipeRepository.findById(id).isPresent()) {
            return;
        }
        if (!userRepository.findById(userFavoriteRecipeRequestDTO.userId()).isPresent()) {
            return;
        }
        if (!recipeRepository.findById(userFavoriteRecipeRequestDTO.recipeId()).isPresent()) {
            return;
        }
        User user = userRepository.findById(userFavoriteRecipeRequestDTO.userId()).get();
        Recipe recipe = recipeRepository.findById(userFavoriteRecipeRequestDTO.recipeId()).get();
        UserFavoriteRecipe userFavoriteRecipe = userFavoriteRecipeMappers.toEntity(userFavoriteRecipeRequestDTO);
        userFavoriteRecipe.setId(id);
        userFavoriteRecipe.setUser(user);
        userFavoriteRecipe.setRecipe(recipe);
        userFavoriteRecipeRepository.save(userFavoriteRecipe);
    }

    public void deleteUserFavoriteRecipe(Integer id) {
        userFavoriteRecipeRepository.deleteById(id);
    }
}
