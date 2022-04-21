package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.application;

import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.UserDetails;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.UserDetailsService;

public class LoginHandler {

    private final UserDetailsService userDetailsService;

    public LoginHandler(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUser(username);
        if (userDetails.getPassword().equals(password)) {
            return userDetails.getUsername();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
