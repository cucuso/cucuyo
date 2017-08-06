package com.cuba.real;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cuba.real.dao.CassandraSession;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@SpringBootApplication
public class Startup {

    @Autowired
    CassandraSession cSession;

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);

    }

    @PostConstruct
    public void init() {
        Cluster cluster = Cluster.builder()
                .addContactPoint("192.168.0.30").build();
        Session session = cluster.connect();

        cSession.setSession(session);
    }

}