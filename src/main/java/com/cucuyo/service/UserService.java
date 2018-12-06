package com.cucuyo.service;

import com.cucuyo.dao.UserDao;
import com.cucuyo.dto.UserRequest;
import com.cucuyo.model.User;

public class UserService {

  UserDao userDao = new UserDao();

  public User createUser(UserRequest user) {

    User dbUser = userDao.getUser(user.getEmail());
    if (dbUser != null)
      throw new IllegalArgumentException("User Already Exists");
    return userDao.createUser(user);
  }

  public User getUserInfo(String email) {
    return userDao.getUser(email);
  }

}
