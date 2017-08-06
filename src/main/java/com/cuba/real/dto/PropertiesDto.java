package com.cuba.real.dto;

import java.util.List;

import com.cuba.real.model.Property;

public class PropertiesDto {

    private List<Property> properties;
    private String pagingObject;

    public PropertiesDto(List<Property> properties, String pagingObject) {
        this.properties = properties;
        this.pagingObject = pagingObject;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getPagingObject() {
        return pagingObject;
    }

    public void setPagingObject(String pagingObject) {
        this.pagingObject = pagingObject;
    }

}
