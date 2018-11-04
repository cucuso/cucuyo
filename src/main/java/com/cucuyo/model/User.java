package com.cucuyo.model;

import java.util.List;

import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "cucuyo", name = "users")
public class User {

    private String email;
    private String password;
    private List<PropertyUdt> properties;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PropertyUdt> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyUdt> properties) {
        this.properties = properties;
    }

}
