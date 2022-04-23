package org.xpdojo.designpatterns._03_behavioral_patterns._01_chain_of_responsibility.middleware;

import org.xpdojo.designpatterns._03_behavioral_patterns._01_chain_of_responsibility.Server;

public class AuthenticationMiddleware extends Middleware {
    private Server server;

    public AuthenticationMiddleware(Server server) {
        this.server = server;
    }

    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }
}
