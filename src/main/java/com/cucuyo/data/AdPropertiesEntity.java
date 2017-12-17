package com.cucuyo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static org.springframework.cassandra.core.Ordering.ASCENDING;
import static org.springframework.cassandra.core.PrimaryKeyType.CLUSTERED;
import static org.springframework.cassandra.core.PrimaryKeyType.PARTITIONED;

@Data
@ToString
@Table("ad_properties")
@EqualsAndHashCode(of = "id")
public class AdPropertiesEntity implements Serializable {

    @PrimaryKeyColumn(type = PARTITIONED, ordinal = 0)
    private UUID id;
    @PrimaryKeyColumn(type = CLUSTERED, ordering = ASCENDING, ordinal = 1)
    private String title;
    private String description;
    private double price;
    private String thumbnailId;
    private List<String> imagesIds;

    static String getTableName() {
        return AdPropertiesEntity.class.getAnnotation(Table.class).value();
    }
}
