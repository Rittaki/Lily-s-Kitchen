package com.recipes.demo.persistence;

import com.recipes.demo.businesslayer.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {
//    Recipe findRecipeById(Long id);
}
