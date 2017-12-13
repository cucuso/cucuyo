package com.cucuyo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@Table("ad_properties")
@EqualsAndHashCode(of = "id")
public class AdPropertiesEntity implements Serializable {

    @PrimaryKey
    private AdPropertiesKey id;
    private String description;
    private double price;
    private String thumbnailId;
    private List<String> imagesIds;

    public UUID getId() {
        return id.getId();
    }

    public String getTitle() {
        return id.getTitle();
    }
}
