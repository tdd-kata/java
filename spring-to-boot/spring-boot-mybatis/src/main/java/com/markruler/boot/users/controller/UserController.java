package com.markruler.boot.users.controller;

import com.markruler.boot.users.application.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getUser(ModelAndView mv) {
        final String username = userService.getUser();
        log.info("Hello, {}", username);

        mv.addObject("user", username);
        mv.setViewName("user");
        return mv;
    }
}
