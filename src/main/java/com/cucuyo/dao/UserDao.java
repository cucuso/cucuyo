package com.cucuyo.dao;

import com.cucuyo.dto.UserRequest;
import com.cucuyo.model.User;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class UserDao {

  // TODO get from environment variable
  private String cassandraContactPoint = "198.199.79.117";

  Session session = getCassandraSession();

  public User createUser(UserRequest userReq) {

    User user = new User();
    user.setEmail(userReq.getEmail());
    user.setPassword(userReq.getPassword());
    getMapper().save(user);
    return user;
  }

  public User getUser(String email) {
    return getMapper().get(email);
  }

  private Mapper<User> getMapper() {
    MappingManager manager = new MappingManager(session);
    return manager.mapper(User.class);
  }

  private Session getCassandraSession() {

    Cluster cluster = Cluster.builder()
        .addContactPoint(cassandraContactPoint)
        .build();

    return cluster.connect();
  }

}
