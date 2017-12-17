package com.cucuyo.data;

import com.cucuyo.core.domain.AdProperties;
import com.datastax.driver.core.Row;
import lombok.val;
import org.mapstruct.Mapper;

@Mapper(uses = UUIDMapper.class)
public interface AdPropertiesEntityMapper {

    AdProperties asAdProperties(AdPropertiesEntity entity);

    default AdPropertiesEntity asAdPropertiesEntity(Row row) {
        val adProperties = new AdPropertiesEntity();
        adProperties.setId(row.getUUID("id"));
        adProperties.setTitle(row.getString("title"));
        adProperties.setDescription(row.getString("description"));
        adProperties.setPrice(row.getDouble("price"));
        return adProperties;
    }
}
