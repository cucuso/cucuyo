package com.cucuyo.model;

import java.util.List;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Data()
@Table(keyspace = "cucuyo", name = "properties")
public class Property {

    @PartitionKey
    private Long id;
    private String address;
    private String description;
    private Double price;
    private List<String> images;
    private Float latitude;
    private Float longitude;

}
