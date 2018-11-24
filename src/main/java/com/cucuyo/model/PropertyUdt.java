package com.cucuyo.model;

import com.datastax.driver.mapping.annotations.UDT;

import lombok.Data;

@Data()
@UDT(keyspace = "cucuyo", name = "property")
public class PropertyUdt {

    private String id;
    private String title;
    private String description;
    private Double price;

}
