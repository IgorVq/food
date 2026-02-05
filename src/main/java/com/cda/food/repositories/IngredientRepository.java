package com.cda.food.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
