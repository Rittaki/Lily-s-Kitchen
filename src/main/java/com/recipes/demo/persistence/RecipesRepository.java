package com.recipes.demo.persistence;

import com.recipes.demo.businesslayer.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {
//    Optional<Recipe> findRecipeById(Long id);
    List<Recipe> findAll();
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
