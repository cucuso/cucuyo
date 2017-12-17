package com.cucuyo.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(of = "id")
public class AdProperties implements Serializable {

    private String id;
    private String title;
    private String description;
    private double price;
}
