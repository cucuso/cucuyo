package com.cucuyo.model;

import java.util.List;

import lombok.Data;

@Data()
//@Table(keyspace = "cucuyo", name = "users")
public class User {

    private String email;
    private String password;
    private List<PropertyUdt> properties;

}
