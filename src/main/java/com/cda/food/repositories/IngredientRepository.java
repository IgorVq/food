package com.cda.food.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cda.food.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findByUser_Id(Integer userId);
    Optional<Ingredient> findByIdAndUser_Id(Integer id, Integer userId);
}
