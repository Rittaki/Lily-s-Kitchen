package com.recipes.demo.businesslayer;

import com.recipes.demo.persistence.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipesRepository recipesRepository;

    @Autowired
    public RecipeService(RecipesRepository recipeRepository) {
        this.recipesRepository = recipeRepository;
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipesRepository.findById(id);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipesRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipesRepository.delete(recipesRepository.findById(id).get());
    }

    public List<Recipe> recipesByCategory(String cat) {
        return recipesRepository.findByCategoryIgnoreCaseOrderByDateDesc(cat);
    }

    public List<Recipe> recipesByContainingInName(String name) {
        return recipesRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
