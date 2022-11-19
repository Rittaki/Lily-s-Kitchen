package com.recipes.demo.presentation;

import com.recipes.demo.businesslayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    @Autowired
    RecipeService service;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        var recipe = service.findRecipeById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        }
        else {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
    }
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(name = "category", required = false) String c,
                                                     @RequestParam(name = "name", required = false) String n) {
        if (!(c == null)) {
            return new ResponseEntity<>(service.recipesByCategory(c), HttpStatus.OK);
        }
        else if (!(n == null)) {
            return new ResponseEntity<>(service.recipesByContainingInName(n), HttpStatus.OK);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
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

    @PostMapping("/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe,
                                        @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByEmail(currentUser.getUsername()).get();
        recipe.setUser(user);
//        Recipe newRecipe = new Recipe(recipe.getId(), recipe.getName(), recipe.getCategory(),
//                recipe.getDescription(), recipe.getDate(), recipe.getIngredients(), recipe.getDirections(), user);
        Recipe recivedRecipe = service.saveRecipe(recipe);
        Long idx = recivedRecipe.getId();
        user.getRecipes().add(recivedRecipe);
        return Map.of("id", idx);
    }
    @DeleteMapping("/{id}")
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

//    @PostMapping("/register")
//    public void register(@RequestBody User user) {
//        // input validation omitted for brevity
//
//        user.setPassword(encoder.encode(user.getPassword()));
//
//        userRepo.save(user);
//    }
}
