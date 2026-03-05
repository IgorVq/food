package com.cda.food.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUser_Id(Integer userId);
    List<Recipe> findByUser_IdOrShareTrue(Integer userId);
    Optional<Recipe> findByIdAndUser_Id(Integer id, Integer userId);
    Optional<Recipe> findByIdAndShareTrue(Integer id);
} 
