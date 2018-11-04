package com.cucuyo.model;

import java.nio.ByteBuffer;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;

@Data()
@Table(keyspace = "cucuyo", name = "properties")
public class Property {

    @PartitionKey
    private Long id;
    private String title;
    private String description;
    private Double price;
    @Column(name="image_one")
    private ByteBuffer imageOne;
    @Column(name="image_two")
    private ByteBuffer imageTwo;
    @Column(name="image_three")
    private ByteBuffer imageThree;

}
