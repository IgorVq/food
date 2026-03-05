package com.cda.food.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cda.food.dtos.RecipeRequestDTO;
import com.cda.food.dtos.RecipeResponseDTO;
import com.cda.food.services.RecipeServices;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping(value = "/recipes", produces = "application/json")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeServices recipeServices;

    @GetMapping
    public ResponseEntity<List<RecipeResponseDTO>> getAllRecipes() {
        return ResponseEntity.ok(recipeServices.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> getRecipeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(recipeServices.getRecipeById(id));
    }

    @GetMapping(value = "/{id}/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> getRecipePdf(@PathVariable("id") Integer id) {
        byte[] pdf = recipeServices.generateRecipePdf(id);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recipe-" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable("id") Integer id, @RequestBody RecipeRequestDTO recipeRequestDTO) {
        recipeServices.updatedRecipe(id, recipeRequestDTO);
        return ResponseEntity.ok("Recipe updated successfully");
    }

    @PostMapping
    public ResponseEntity<String> createRecipe(@RequestBody RecipeRequestDTO recipeRequestDTO) {
        recipeServices.createRecipe(recipeRequestDTO);
        return ResponseEntity.ok("Recipe created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable("id") Integer id) {
        recipeServices.deleteRecipe(id);
        return ResponseEntity.ok("Recipe deleted successfully");
    }
}
