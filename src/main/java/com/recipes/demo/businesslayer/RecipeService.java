package com.recipes.demo.businesslayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.demo.persistence.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public List<Recipe> getRecipes() {
        return recipesRepository.findAll();
    }
    public List<Recipe> getMyRecipes(Long id) {
        return recipesRepository.findAllByUserId(id);
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

    public Recipe getJson(String rec) {
        Recipe recipeJson = new Recipe();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            recipeJson = objectMapper.readValue(rec, Recipe.class);
        } catch (IOException err) {
            System.out.printf("Error", err.toString());
        }

        return recipeJson;
    }
}
