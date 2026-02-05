package com.cda.food.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
}
