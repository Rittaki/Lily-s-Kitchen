package com.recipes.demo;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

import java.util.*;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String name;
    private String description;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;

    public Recipe() {

    }
    public Recipe(String name, String description, ArrayList<String> ingredients, ArrayList<String> directions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                '}';
    }
}
