package com.cucuyo.data;

import lombok.Data;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

@Data
@PrimaryKeyClass
public class AdPropertiesKey implements Serializable {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID id;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private String title;

    public AdPropertiesKey() {
    }

    public AdPropertiesKey(String title) {
        this.title = title;
        id = UUID.randomUUID();
    }

    public AdPropertiesKey(UUID id, String title) {
        this.id = id;
        this.title = title;
    }
}
