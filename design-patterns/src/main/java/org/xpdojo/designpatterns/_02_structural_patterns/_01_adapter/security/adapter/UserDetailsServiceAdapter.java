package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adapter;

import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adaptee.AccountService;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.UserDetails;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.UserDetailsService;

public class UserDetailsServiceAdapter implements UserDetailsService {

    // Adaptee
    private final AccountService accountService;

    public UserDetailsServiceAdapter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUser(String username) {
        return new UserDetailsAdapter(accountService.findAccountByUsername(username));
    }
}
