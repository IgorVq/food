package com.cda.food.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cda.food.dtos.UserFavoriteRecipeRequestDTO;
import com.cda.food.dtos.UserFavoriteRecipeResponseDTO;
import com.cda.food.services.UserFavoriteRecipeServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/user-favorite-recipes", produces = "application/json")
@RequiredArgsConstructor
public class UserFavoriteRecipeController {

    private final UserFavoriteRecipeServices userFavoriteRecipeServices;

    @GetMapping
    public ResponseEntity<List<UserFavoriteRecipeResponseDTO>> getAllUserFavoriteRecipes() {
        return ResponseEntity.ok(userFavoriteRecipeServices.getAllUserFavoriteRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFavoriteRecipeResponseDTO> getUserFavoriteRecipeById(@PathVariable("id") Integer id) {
        UserFavoriteRecipeResponseDTO userFavoriteRecipe = userFavoriteRecipeServices.getUserFavoriteRecipeById(id);
        return userFavoriteRecipe != null ? ResponseEntity.ok(userFavoriteRecipe) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createUserFavoriteRecipe(@RequestBody UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO) {
        userFavoriteRecipeServices.createUserFavoriteRecipe(userFavoriteRecipeRequestDTO);
        return ResponseEntity.ok("UserFavoriteRecipe created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserFavoriteRecipe(@PathVariable("id") Integer id, @RequestBody UserFavoriteRecipeRequestDTO userFavoriteRecipeRequestDTO) {
        userFavoriteRecipeServices.updateUserFavoriteRecipe(id, userFavoriteRecipeRequestDTO);
        return ResponseEntity.ok("UserFavoriteRecipe updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserFavoriteRecipe(@PathVariable("id") Integer id) {
        userFavoriteRecipeServices.deleteUserFavoriteRecipe(id);
        return ResponseEntity.ok("UserFavoriteRecipe deleted successfully");
    }
}
