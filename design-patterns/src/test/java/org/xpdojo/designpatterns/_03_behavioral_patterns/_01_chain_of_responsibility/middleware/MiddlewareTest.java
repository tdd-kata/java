package org.xpdojo.designpatterns._03_behavioral_patterns._01_chain_of_responsibility.middleware;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._03_behavioral_patterns._01_chain_of_responsibility.Server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MiddlewareTest {

    @Test
    void sut_chain_of_authentication() {
        Server server = new Server();

        Middleware middleware = new ThrottlingMiddleware(4);
        middleware
                .linkWith(new AuthenticationMiddleware(server))
                .linkWith(new RoleCheckMiddleware());

        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");
        server.setMiddleware(middleware);

        boolean valid = server.logIn("user@example.com", "user_pass");
        assertThat(valid).isTrue();

        boolean admin = server.logIn("admin@example.com", "admin_pass");
        assertThat(admin).isTrue();

        boolean emailNotFound = server.logIn("not_found@example.com", "user_pass");
        assertThat(emailNotFound).isFalse();

        boolean wrongPassword = server.logIn("user@example.com", "wrong");
        assertThat(wrongPassword).isFalse();

        assertThatThrownBy(() -> server.logIn("user@example.com", "user_pass"))
                .isInstanceOf(ThreadDeath.class);
    }
}
