package com.cuba.real.dao;

import org.springframework.stereotype.Component;

import com.datastax.driver.core.Session;

@Component
public class CassandraSession {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
