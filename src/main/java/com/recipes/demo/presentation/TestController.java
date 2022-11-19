package com.recipes.demo.presentation;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        return "/test is accessed";
    }

    @GetMapping("/username")
    public void username(@AuthenticationPrincipal UserDetails details) {
        System.out.println(details.getUsername());
    }

    @GetMapping("/details")
    public void details(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();

        System.out.println("Username: " + details.getUsername());
        System.out.println("User has authorities/roles: " + details.getAuthorities());
    }
}