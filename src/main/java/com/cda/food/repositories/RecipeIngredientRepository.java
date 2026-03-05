package com.cda.food.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    List<RecipeIngredient> findByRecipe_User_Id(Integer userId);
    Optional<RecipeIngredient> findByIdAndRecipe_User_Id(Integer id, Integer userId);
}
