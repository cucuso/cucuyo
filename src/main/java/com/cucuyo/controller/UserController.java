package com.cucuyo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cucuyo.dao.UserDao;
import com.cucuyo.model.User;

@RestController
public class UserController {

    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User getProperties(@RequestBody User user) {
        return userDao.createUser(user);
    }


}
