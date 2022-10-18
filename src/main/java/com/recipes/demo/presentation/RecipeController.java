package com.recipes.demo.presentation;

import com.recipes.demo.businesslayer.Recipe;
import com.recipes.demo.businesslayer.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeController {
    @Autowired
    RecipeService service;

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        var recipe = service.findRecipeById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        }
        else {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
    }
    @GetMapping("/api/recipe/search")
    @ResponseBody
    public List<Recipe> searchRecipe(@RequestParam(name = "category") String c,
                                     @RequestParam String category) {
        return service.recipesByCategory(category);
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id) {
        var currentRecipe = service.findRecipeById(id);
        if (currentRecipe.isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            recipe.setId(id);
            recipe.setDate(now);
            service.saveRecipe(recipe);
            throw new RecipeNoContentException("Updated for id = " + id);
        }
        else {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe) {
        Long idx = service.saveRecipe(recipe).getId();
        return Map.of("id", idx);
    }
    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        var recipe = service.findRecipeById(id);
        if (recipe.isPresent()) {
            service.deleteRecipe(id);
            throw new RecipeNoContentException("Deleted for id = " + id);
        }
        else {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
    }
}
