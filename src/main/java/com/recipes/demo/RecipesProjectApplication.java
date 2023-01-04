package com.recipes.demo;

import com.recipes.demo.presentation.RecipeController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RecipesProjectApplication {
	public static void main(String[] args) {
//		new File(RecipeController.uploadDirectory).mkdir();
		SpringApplication.run(RecipesProjectApplication.class, args);
	}
}
