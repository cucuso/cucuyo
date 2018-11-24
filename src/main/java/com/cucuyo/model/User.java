package com.cucuyo.model;

import java.util.List;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Data()
@Table(keyspace = "cucuyo", name = "users")
public class User {

  @PartitionKey
  private String email;
  private String password;
  private List<PropertyUdt> properties;

}
