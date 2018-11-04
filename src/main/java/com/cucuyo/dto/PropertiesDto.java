package com.cucuyo.dto;

import java.util.List;

import com.cucuyo.model.Property;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor()
@Data()
public class PropertiesDto {

    private List<Property> properties;
    private String pagingObject;

}
