package com.recipes.demo.businesslayer;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Recipes")
public class Recipe {
    @Id
    @GeneratedValue
    @JsonIgnore
    @Column
    private Long id;
    @NotBlank
    @Column(name = "Name")
    private String name;
    @NotBlank
    @Column(name = "Category")
    private String category;
    @NotBlank
    @Column(name = "Description")
    private String description;
    @Column(columnDefinition = "TIMESTAMP", name = "Date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime date;
    @NotEmpty
    @Column(name = "Ingredients")
    private ArrayList<String> ingredients;
    @NotEmpty
    @Column(name = "Directions")
    private ArrayList<String> directions;
    @ManyToOne
    @JsonIgnore
//    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
