package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cda.food.dtos.IngredientRequestDTO;
import com.cda.food.dtos.IngredientResponseDTO;
import com.cda.food.entities.Ingredient;
import com.cda.food.entities.User;
import com.cda.food.mappers.IngredientMappers;
import com.cda.food.repositories.IngredientRepository;
import com.cda.food.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IngredientServices {
    private final IngredientRepository ingredientRepository;
    private final IngredientMappers ingredientMappers;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    public List<IngredientResponseDTO> getAllIngredients() {
        User currentUser = currentUserService.getCurrentUser();
        List<Ingredient> ingredients = currentUser.getRole() == User.Role.ADMIN
            ? ingredientRepository.findAll()
            : ingredientRepository.findByUser_Id(currentUser.getId());
        return ingredientMappers.toDtoList(ingredients);
    }

    public IngredientResponseDTO getIngredientById(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        Ingredient ingredient = currentUser.getRole() == User.Role.ADMIN
            ? ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient not found"))
            : ingredientRepository.findByIdAndUser_Id(id, currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient not found"));
        return ingredientMappers.toDto(ingredient);
    }

    public void createIngredient(IngredientRequestDTO ingredientRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        User owner = currentUser;
        if (currentUser.getRole() == User.Role.ADMIN && ingredientRequestDTO.userId() != null) {
            owner = userRepository.findById(ingredientRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        }
        Ingredient ingredient = ingredientMappers.toEntity(ingredientRequestDTO);
        ingredient.setUser(owner);
        ingredientRepository.save(ingredient);
    }

    public void updateIngredient(Integer id, IngredientRequestDTO ingredientRequestDTO) {
        User currentUser = currentUserService.getCurrentUser();
        Ingredient existingIngredient = ingredientRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient not found"));
        if (currentUser.getRole() != User.Role.ADMIN
            && (existingIngredient.getUser() == null || !existingIngredient.getUser().getId().equals(currentUser.getId()))) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot edit another user's ingredient");
        }

        User owner = existingIngredient.getUser();
        if (currentUser.getRole() == User.Role.ADMIN && ingredientRequestDTO.userId() != null) {
            owner = userRepository.findById(ingredientRequestDTO.userId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        }
        Ingredient ingredientToUpdate = ingredientMappers.toEntity(ingredientRequestDTO);
        ingredientToUpdate.setId(id);
        ingredientToUpdate.setUser(owner);
        ingredientRepository.save(ingredientToUpdate);
    }

    public void deleteIngredient(Integer id) {
        User currentUser = currentUserService.getCurrentUser();
        Ingredient ingredient = ingredientRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingredient not found"));
        if (currentUser.getRole() != User.Role.ADMIN
            && (ingredient.getUser() == null || !ingredient.getUser().getId().equals(currentUser.getId()))) {
            throw new ResponseStatusException(FORBIDDEN, "You cannot delete another user's ingredient");
        }
        ingredientRepository.deleteById(id);
    }
}
