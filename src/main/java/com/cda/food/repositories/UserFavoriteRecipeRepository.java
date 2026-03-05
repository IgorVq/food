package com.cda.food.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.UserFavoriteRecipe;

public interface UserFavoriteRecipeRepository extends JpaRepository<UserFavoriteRecipe, Integer> {
    List<UserFavoriteRecipe> findByUser_Id(Integer userId);
    Optional<UserFavoriteRecipe> findByIdAndUser_Id(Integer id, Integer userId);
}
