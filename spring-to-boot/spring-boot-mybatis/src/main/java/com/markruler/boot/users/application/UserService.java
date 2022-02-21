package com.markruler.boot.users.application;

import com.markruler.boot.users.persistence.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getUser() {
        return userDao.getUser();
    }
}
