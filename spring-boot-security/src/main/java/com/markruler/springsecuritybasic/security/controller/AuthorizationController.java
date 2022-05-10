package com.markruler.springsecuritybasic.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin/pay")
    public String adminPay() {
        return "adminPay";
    }

    @GetMapping("/admin/**")
    public String admin() {
        return "admin";
    }
}
