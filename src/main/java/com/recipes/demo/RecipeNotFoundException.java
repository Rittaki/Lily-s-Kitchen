package com.recipes.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String cause) {
        super(cause);
    }
}

