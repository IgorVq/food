package com.cda.food.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

} 