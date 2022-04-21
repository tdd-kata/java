package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adapter;

import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adaptee.Account;
import org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.UserDetails;

public class UserDetailsAdapter implements UserDetails {

    // Adaptee
    private final Account account;

    public UserDetailsAdapter(Account account) {
        this.account = account;
    }

    @Override
    public String getUsername() {
        return account.getName();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }
}
