package com.markruler.legacy.user.application;

import com.markruler.legacy.user.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String getUser() {
        return userMapper.getUser();
    }
}
