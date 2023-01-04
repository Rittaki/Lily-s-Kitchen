package com.recipes.demo.presentation;

import com.recipes.demo.businesslayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    @Value("${uploadDir}")
    private String uploadFolder;
//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    RecipeService service;
    @Autowired
    UserService userService;
    @Autowired
    FileDataService fileDataService;

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

    @GetMapping("/")
    public ResponseEntity<?> downloadFile(@RequestParam(name = "filename") String fileName) throws IOException {
        FileData file = fileDataService.findFileByName(fileName).get();
        String type = file.getType();
        byte[] fileData = fileDataService.downloadFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(type)).body(fileData);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> allRecipes(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(service.getRecipes(), HttpStatus.OK);
    }

    @GetMapping("/allMine")
    public ResponseEntity<List<Recipe>> allMineRecipes(@AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByEmail(currentUser.getUsername()).get();
        Long id = user.getId();
        return new ResponseEntity<>(service.getMyRecipes(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id,
                             @AuthenticationPrincipal UserDetails currentUser) {
        var currentRecipe = service.findRecipeById(id);
        User user = userService.findUserByEmail(currentUser.getUsername()).get();
        if (currentRecipe.isPresent()) {
            if (currentRecipe.get().getUser().getEmail() != user.getEmail()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden access");
            }
            LocalDateTime now = LocalDateTime.now();
            recipe.setId(id);
            recipe.setDate(now);
            recipe.setUser(user);
            service.saveRecipe(recipe);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("message", "Updated for id = " + id);
            return new ResponseEntity<>(body, HttpStatus.OK);
//            throw new RecipeNoContentException("Updated for id = " + id);
        }
        else {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
    }

    @PostMapping("/new")
//    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe,
//                                        @AuthenticationPrincipal UserDetails currentUser) {
    public ResponseEntity<Recipe> postRecipe(@Valid @RequestBody Recipe recipe,
//            @RequestParam(name = "name", required = false) String nam,
//                                             @RequestParam(name = "category", required = false) String category,
//                                             @RequestParam(name = "description", required = false) String description,
//                                             @RequestParam(name = "directions", required = false) ArrayList<String> directions,
//                                             @RequestParam(name = "ingredients", required = false) ArrayList<String> ingredients,
//                                        @RequestParam(name = "file", required = false) MultipartFile file,
                                        @AuthenticationPrincipal UserDetails currentUser

    ) {
        User user = userService.findUserByEmail(currentUser.getUsername()).get();
        recipe.setUser(user);
//        if (recipe.getImage() == null) {
//            recipe.setImage("no-image.png");
//        }
        Recipe recivedRecipe = service.saveRecipe(recipe);
//        Long idx = recivedRecipe.getId();
//        user.getRecipes().add(recivedRecipe);
//        return Map.of("id", idx);
        return new ResponseEntity<>(recivedRecipe, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>("file uploaded", HttpStatus.OK);
    }

    @PostMapping(value = "/stam", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Recipe> stam(@RequestPart("file") MultipartFile file,
                                       @RequestPart("recipe") String rec,
                                       @AuthenticationPrincipal UserDetails currentUser) throws IOException {
        Recipe recipe = service.getJson(rec);
//        Recipe recipe = new Recipe();
        User user = userService.findUserByEmail(currentUser.getUsername()).get();
//        recipe.setId(7L);
        recipe.setUser(user);
        String filePath = fileDataService.uploadFile(file);
        recipe.setFileData(filePath);
        Recipe recivedRecipe = service.saveRecipe(recipe);
        return new ResponseEntity<>(recivedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByEmail(currentUser.getUsername()).get();
        var recipe = service.findRecipeById(id);
        if (recipe.isPresent()) {
            if ((recipe.get().getUser() != null)
                    && (recipe.get().getUser().getEmail() != user.getEmail())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden access");
            }
            service.deleteRecipe(id);
            throw new RecipeNoContentException("Deleted for id = " + id);
        }
        else {
            throw new RecipeNotFoundException("Recipe not found for id = " + id);
        }
    }
}
