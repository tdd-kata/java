package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security;

public interface UserDetailsService {

    UserDetails loadUser(String username);

}
