package com.cucuyo.service;

import com.cucuyo.dao.PropertyDao;
import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.model.Property;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class PropertyService {

  PropertyDao propertyDao = new PropertyDao();

  public PropertiesDto getProperties(SearchDto search, String page) {
    log.info("Retrieving properties for searchDto <%s> and page <%s>", search, page);
    return propertyDao.getProperties(search, page);
  }

  public Property getProperty(String id) {
    return propertyDao.getProperty(id);
  }

  public Property writeProperty(Property property) {
    return propertyDao.saveProperty(property);
  }
}
