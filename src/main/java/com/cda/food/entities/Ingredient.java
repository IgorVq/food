package com.cda.food.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LIBELLE", nullable = false)
    private String libelle;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientType type;

    @Column(name = "CALORIE_COUNT", nullable = false)
    private Integer calorieCount;

    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> recipeIngredients;

    public enum IngredientType {
        LEGUME,
        VIANDE,
        POISSON,
        FECULENT,
        PRODUIT_LAITIER,
        FRUIT,
        EPICE,
        HUILE,
        AUTRE
    }
}
