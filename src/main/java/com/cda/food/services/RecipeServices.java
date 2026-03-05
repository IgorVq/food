package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cda.food.dtos.RecipeRequestDTO;
import com.cda.food.dtos.RecipeResponseDTO;
import com.cda.food.entities.Recipe;
import com.cda.food.entities.User;
import com.cda.food.mappers.RecipeMappers;
import com.cda.food.repositories.RecipeRepository;
import com.cda.food.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecipeServices {
    private final RecipeRepository recipeRepository;
    private final RecipeMappers recipeMappers;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public List<RecipeResponseDTO> getAllRecipes() {
        User currentUser = currentUserService.getCurrentUser();
        List<Recipe> recipes = currentUser.getRole() == User.Role.ADMIN
            ? recipeRepository.findAll()
            : recipeRepository.findByUser_IdOrShareTrue(currentUser.getId());
        return recipeMappers.toDtoList(recipes);
    }

    public RecipeResponseDTO getRecipeById(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe recipe = currentUser.getRole() == User.Role.ADMIN
            ? recipeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"))
            : recipeRepository.findByIdAndUser_Id(id, currentUser.getId())
                .or(() -> recipeRepository.findByIdAndShareTrue(id))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));
        return recipeMappers.toDto(recipe);
    }

    public void createRecipe(RecipeRequestDTO recipeRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        User user = currentUser;
        if (currentUser.getRole() == User.Role.ADMIN && recipeRequestDTO.userId() != null) {
            user = userRepository.findById(recipeRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        }
        Recipe recipe = recipeMappers.toEntity(recipeRequestDTO);
        recipe.setUser(user);
        recipeRepository.save(recipe);
    }

    public void updatedRecipe(Integer id, RecipeRequestDTO recipeRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe existingRecipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !existingRecipe.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot edit another user's recipe");
        }

        User user = existingRecipe.getUser();
        if (currentUser.getRole() == User.Role.ADMIN && recipeRequestDTO.userId() != null) {
            user = userRepository.findById(recipeRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        }
        Recipe recipeToUpdate = recipeMappers.toEntity(recipeRequestDTO);
        recipeToUpdate.setId(id);
        recipeToUpdate.setUser(user);
        recipeRepository.save(recipeToUpdate);
    }

    public void deleteRecipe(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !recipe.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot delete another user's recipe");
        }
        recipeRepository.deleteById(id);
    }
}
