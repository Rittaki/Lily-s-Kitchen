package com.recipes.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
class RecipeNoContentException extends RuntimeException {
    public RecipeNoContentException(String cause) {
        super(cause);
    }
}

