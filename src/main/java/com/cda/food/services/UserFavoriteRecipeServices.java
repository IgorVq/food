package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserFavoriteRecipeServices {
    private final UserFavoriteRecipeRepository userFavoriteRecipeRepository;
    private final UserFavoriteRecipeMappers userFavoriteRecipeMappers;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final CurrentUserService currentUserService;

    public List<UserFavoriteRecipeResponseDTO> getAllUserFavoriteRecipes() {
        User currentUser = currentUserService.getCurrentUser();
        List<UserFavoriteRecipe> userFavoriteRecipes = currentUser.getRole() == User.Role.ADMIN
            ? userFavoriteRecipeRepository.findAll()
            : userFavoriteRecipeRepository.findByUser_Id(currentUser.getId());
        return userFavoriteRecipeMappers.toDtoList(userFavoriteRecipes);
    }

    public UserFavoriteRecipeResponseDTO getUserFavoriteRecipeById(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        UserFavoriteRecipe userFavoriteRecipe = currentUser.getRole() == User.Role.ADMIN
            ? userFavoriteRecipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Favorite recipe not found"))
            : userFavoriteRecipeRepository.findByIdAndUser_Id(id, currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Favorite recipe not found"));
        return userFavoriteRecipeMappers.toDto(userFavoriteRecipe);
    }

    public void createUserFavoriteRecipe(UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe recipe = recipeRepository.findById(userFavoriteRecipeRequestDTO.recipeId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));

        User user = currentUser;
        if (currentUser.getRole() == User.Role.ADMIN && userFavoriteRecipeRequestDTO.userId() != null) {
            user = userRepository.findById(userFavoriteRecipeRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        }

        UserFavoriteRecipe userFavoriteRecipe = userFavoriteRecipeMappers.toEntity(userFavoriteRecipeRequestDTO);
        userFavoriteRecipe.setUser(user);
        userFavoriteRecipe.setRecipe(recipe);
        userFavoriteRecipeRepository.save(userFavoriteRecipe);
    }

    public void updateUserFavoriteRecipe(Integer id, UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        UserFavoriteRecipe existingFavorite = userFavoriteRecipeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Favorite recipe not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !existingFavorite.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot edit another user's favorites");
        }

        Recipe recipe = recipeRepository.findById(userFavoriteRecipeRequestDTO.recipeId())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Recipe not found"));

        User user = existingFavorite.getUser();
        if (currentUser.getRole() == User.Role.ADMIN && userFavoriteRecipeRequestDTO.userId() != null) {
            user = userRepository.findById(userFavoriteRecipeRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        }

        UserFavoriteRecipe userFavoriteRecipe = userFavoriteRecipeMappers.toEntity(userFavoriteRecipeRequestDTO);
        userFavoriteRecipe.setId(id);
        userFavoriteRecipe.setUser(user);
        userFavoriteRecipe.setRecipe(recipe);
        userFavoriteRecipeRepository.save(userFavoriteRecipe);
    }

    public void deleteUserFavoriteRecipe(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        UserFavoriteRecipe existingFavorite = userFavoriteRecipeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Favorite recipe not found"));
        if (currentUser.getRole() != User.Role.ADMIN && !existingFavorite.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot delete another user's favorites");
        }
        userFavoriteRecipeRepository.deleteById(id);
    }
}
