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
}
