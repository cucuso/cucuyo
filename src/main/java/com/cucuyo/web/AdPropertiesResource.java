package com.cucuyo.web;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class AdPropertiesResource extends ResourceSupport {

    private String uuid;
    private String title;
    private String description;
    private double price;


}
