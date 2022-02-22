package com.markruler.legacy.user.controller;

import com.markruler.legacy.user.application.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ModelAndView getUser(ModelAndView mv, HttpSession httpSession) {
        final String username = userService.getUser();
        log.info("Hello, {}", username);
        httpSession.setAttribute("test-session", username);

        mv.addObject("user", username);
        mv.setViewName("user");
        return mv;
    }
}
