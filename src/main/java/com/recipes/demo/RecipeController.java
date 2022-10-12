package com.recipes.demo;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RecipeController {
    private int id = 0;
    private final Map<Integer, Recipe> recipesMap = new HashMap<>();

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        if (recipesMap.size() < id || id == 0) {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
        return recipesMap.get(id);
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Integer> postRecipe(@RequestBody Recipe recipe) {
        for (var entry : recipesMap.entrySet()) {
            if (entry.getValue().getName().equals(recipe.getName())) {
                recipe.setId(entry.getValue().getId());
                recipesMap.put(entry.getKey(), recipe);
                return Map.of("id", recipe.getId());
            }
        }
        recipe.setId(++id);
        recipesMap.put(recipe.getId(), recipe);
        return Map.of("id", recipe.getId());
//        return String.format(
//                    "Hello! You've added %s recipe!",
//                    recipe.getName()
//            );
    }
}
