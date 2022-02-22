package com.markruler.boot.users.application;

import com.markruler.boot.users.persistence.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String getUser() {
        return userMapper.getUser();
    }
}
