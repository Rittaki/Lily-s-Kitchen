package com.recipes.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RecipesProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipesProjectApplication.class, args);

	}

//	@Bean
//	public CommandLineRunner run(RecipesRepository repository) {
//		return (args) -> {
//			insertTwoRecipes(repository);
//			System.out.println(repository.findAll());
//		};
//	}

//	@Component
//	public class Runner implements CommandLineRunner {
//		private final RecipesRepository repository;
//
//		public Runner(RecipesRepository repository) {
//			this.repository = repository;
//		}
//		@Override
//		public void run(String... args) {
//			insertTwoRecipes(repository);
//			System.out.println(repository.findAll());
//		}
//
//		private void insertTwoRecipes(RecipesRepository repository) {
//			repository.save(new Recipe("Warming Ginger Tea", "Ginger tea is a warming drink for cool weather, ...",
//					new ArrayList<>(List.of("1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey")),
//					new ArrayList<>(List.of("Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)",
//							"Steep for 5-10 minutes", "Drink and enjoy"))));
//			repository.save(new Recipe("Fresh Mint Tea", "Light, aromatic and refreshing beverage, ...",
//					new ArrayList<>(List.of("boiled water", "honey", "fresh mint leaves")),
//					new ArrayList<>(List.of("Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves",
//							"Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"))));
//		}
//	}

}
