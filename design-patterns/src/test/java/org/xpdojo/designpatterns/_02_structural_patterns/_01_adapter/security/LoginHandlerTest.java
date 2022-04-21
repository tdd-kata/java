package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adaptee.AccountService;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adapter.UserDetailsServiceAdapter;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.application.LoginHandler;

import static org.assertj.core.api.Assertions.assertThat;

class LoginHandlerTest {

    @Test
    void sut_account_authentication() {
        AccountService accountService = new AccountService();
        UserDetailsService userDetailsService = new UserDetailsServiceAdapter(accountService);
        LoginHandler loginHandler = new LoginHandler(userDetailsService);

        String loginUser = loginHandler.login("markruler", "password");

        assertThat(loginUser).isEqualTo("markruler");
    }

}
