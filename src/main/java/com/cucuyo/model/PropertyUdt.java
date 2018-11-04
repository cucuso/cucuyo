package com.cucuyo.model;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "cucuyo", name = "property")
public class PropertyUdt {

    private String id;
    private String title;
    private String description;
    private Double price;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
