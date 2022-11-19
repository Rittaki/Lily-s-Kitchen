package com.recipes.demo.presentation;

import com.recipes.demo.businesslayer.User;
import com.recipes.demo.businesslayer.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity<Object> register(@Valid @RequestBody User user) {
        // input validation omitted for brevity
        if (service.findUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));

        service.saveUser(user);
        return ResponseEntity.ok().build();
    }
}