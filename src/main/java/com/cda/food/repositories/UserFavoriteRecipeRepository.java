package com.cda.food.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.UserFavoriteRecipe;

public interface UserFavoriteRecipeRepository extends JpaRepository<UserFavoriteRecipe, Integer> {
    
}
