package com.cucuyo.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataAdPropertiesRepository extends CrudRepository<AdPropertiesEntity, AdPropertiesKey> {

    List<AdPropertiesEntity> findAllByTitleContaining(String title);
}
