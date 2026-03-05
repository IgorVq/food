package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cda.food.dtos.RecipeIngredientRequestDTO;
import com.cda.food.dtos.RecipeIngredientResponseDTO;
import com.cda.food.entities.Ingredient;
import com.cda.food.entities.Recipe;
import com.cda.food.entities.RecipeIngredient;
import com.cda.food.entities.User;
import com.cda.food.mappers.RecipeIngredientMappers;
import com.cda.food.repositories.IngredientRepository;
import com.cda.food.repositories.RecipeIngredientRepository;
import com.cda.food.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecipeIngredientServices {
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMappers recipeIngredientMappers;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final CurrentUserService currentUserService;

    public List<RecipeIngredientResponseDTO> getAllRecipeIngredients() {
        User currentUser = currentUserService.getCurrentUser();
        List<RecipeIngredient> recipeIngredients = currentUser.getRole() == User.Role.ADMIN
            ? recipeIngredientRepository.findAll()
            : recipeIngredientRepository.findByRecipe_User_Id(currentUser.getId());
        return recipeIngredientMappers.toDtoList(recipeIngredients);
    }

    public RecipeIngredientResponseDTO getRecipeIngredientById(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        RecipeIngredient recipeIngredient = currentUser.getRole() == User.Role.ADMIN
            ? recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe ingredient not found"))
            : recipeIngredientRepository.findByIdAndRecipe_User_Id(id, currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe ingredient not found"));
        return recipeIngredientMappers.toDto(recipeIngredient);
    }

    public void createRecipeIngredient(RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe recipe = recipeRepository.findById(recipeIngredientRequestDTO.recipeId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !recipe.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot edit another user's recipe ingredients");
        }
        Ingredient ingredient = ingredientRepository.findById(recipeIngredientRequestDTO.ingredientId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient not found"));
        RecipeIngredient recipeIngredient = recipeIngredientMappers.toEntity(recipeIngredientRequestDTO);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredientRepository.save(recipeIngredient);
    }

    public void updateRecipeIngredient(Integer id, RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        RecipeIngredient existingRecipeIngredient = recipeIngredientRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe ingredient not found"));
        if (currentUser.getRole() != User.Role.ADMIN
            && !existingRecipeIngredient.getRecipe().getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot edit another user's recipe ingredients");
        }

        Recipe recipe = recipeRepository.findById(recipeIngredientRequestDTO.recipeId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !recipe.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot reassign recipe ingredients to another user's recipe");
        }
        Ingredient ingredient = ingredientRepository.findById(recipeIngredientRequestDTO.ingredientId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient not found"));
        RecipeIngredient recipeIngredient = recipeIngredientMappers.toEntity(recipeIngredientRequestDTO);
        recipeIngredient.setId(id);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredientRepository.save(recipeIngredient);
    }

    public void deleteRecipeIngredient(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe ingredient not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !recipeIngredient.getRecipe().getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot delete another user's recipe ingredients");
        }
        recipeIngredientRepository.deleteById(id);
    }
}
