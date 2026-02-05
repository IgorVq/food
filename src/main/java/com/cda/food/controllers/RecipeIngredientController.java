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

import com.cda.food.dtos.RecipeIngredientRequestDTO;
import com.cda.food.dtos.RecipeIngredientResponseDTO;
import com.cda.food.services.RecipeIngredientServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/recipe-ingredients", produces = "application/json")
@RequiredArgsConstructor
public class RecipeIngredientController {

    private final RecipeIngredientServices recipeIngredientServices;

    @GetMapping
    public ResponseEntity<List<RecipeIngredientResponseDTO>> getAllRecipeIngredients() {
        return ResponseEntity.ok(recipeIngredientServices.getAllRecipeIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeIngredientResponseDTO> getRecipeIngredientById(@PathVariable("id") Integer id) {
        RecipeIngredientResponseDTO recipeIngredient = recipeIngredientServices.getRecipeIngredientById(id);
        return recipeIngredient != null ? ResponseEntity.ok(recipeIngredient) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createRecipeIngredient(@RequestBody RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        recipeIngredientServices.createRecipeIngredient(recipeIngredientRequestDTO);
        return ResponseEntity.ok("RecipeIngredient created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipeIngredient(@PathVariable("id") Integer id, @RequestBody RecipeIngredientRequestDTO recipeIngredientRequestDTO) {
        recipeIngredientServices.updateRecipeIngredient(id, recipeIngredientRequestDTO);
        return ResponseEntity.ok("RecipeIngredient updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipeIngredient(@PathVariable("id") Integer id) {
        recipeIngredientServices.deleteRecipeIngredient(id);
        return ResponseEntity.ok("RecipeIngredient deleted successfully");
    }
}
