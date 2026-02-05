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

import com.cda.food.dtos.IngredientRequestDTO;
import com.cda.food.dtos.IngredientResponseDTO;
import com.cda.food.services.IngredientServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/ingredients", produces = "application/json")
@RequiredArgsConstructor
public class IngredientController {
    
    private final IngredientServices ingredientServices;

    @GetMapping
    public ResponseEntity<List<IngredientResponseDTO>> getAllIngredients() {
        return ResponseEntity.ok(ingredientServices.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> getIngredientById(@PathVariable("id") Integer id) {
        IngredientResponseDTO ingredient = ingredientServices.getIngredientById(id);
        return ingredient != null ? ResponseEntity.ok(ingredient) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createIngredient(@RequestBody IngredientRequestDTO ingredientRequestDTO) {
        ingredientServices.createIngredient(ingredientRequestDTO);
        return ResponseEntity.ok("Ingredient created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateIngredient(@PathVariable("id") Integer id, @RequestBody IngredientRequestDTO ingredientRequestDTO) {
        ingredientServices.updateIngredient(id, ingredientRequestDTO);
        return ResponseEntity.ok("Ingredient updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable("id") Integer id) {
        ingredientServices.deleteIngredient(id);
        return ResponseEntity.ok("Ingredient deleted successfully");
    }
}
