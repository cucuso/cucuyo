package com.cucuyo.service;

import com.cucuyo.dao.UserDao;
import com.cucuyo.dto.UserRequest;
import com.cucuyo.model.User;


public class UserService {


    UserDao userDao = new UserDao();

    public User createUser(UserRequest user) {
        return userDao.createUser(user);
    }

    public User getUserInfo(String email) {
      return userDao.getUser(email);
    }

}
