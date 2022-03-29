package com.markruler.springsecuritybasic.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/denied")
    public String denied() {
        return "Access is denied";
    }
}
