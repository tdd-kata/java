package org.xpdojo.spring.validation;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDto findUser(UserDto user) {
        return user;
    }
}
