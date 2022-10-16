package com.recipes.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RecipeController {
    private Long id = 0L;
    private final Map<Long, Recipe> recipesMap = new HashMap<>();
    private final RecipesRepository repository;
    public RecipeController(RecipesRepository repository) {
			this.repository = repository;
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
//        Recipe ans = repository.findById(id).get();
        return repository.findById(id).get();

//        if (recipesMap.size() < id || id == 0) {
//            throw new RecipeNotFoundException("Recipe not found for id = " + id);
//        }
//        return recipesMap.get(id);
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@RequestBody Recipe recipe) {
        Long idx = repository.save(new Recipe(recipe.getName(), recipe.getDescription(),
                recipe.getIngredients(), recipe.getDirections())).getId();

//        for (var entry : recipesMap.entrySet()) {
//            if (entry.getValue().getName().equals(recipe.getName())) {
//                recipe.setId(entry.getValue().getId());
//                recipesMap.put(entry.getKey(), recipe);
//                return Map.of("id", recipe.getId());
//            }
//        }
//        recipe.setId(++id);
//        recipesMap.put(recipe.getId(), recipe);
//        return Map.of("id", recipe.getId());
        return Map.of("id", idx);

//        return String.format(
//                    "Hello! You've added %s recipe!",
//                    recipe.getName()
//            );
    }
    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
        else {
            repository.delete(repository.findById(id).get());
            throw new RecipeNoContentException("Deleted for id = " + id);
        }
    }
}
