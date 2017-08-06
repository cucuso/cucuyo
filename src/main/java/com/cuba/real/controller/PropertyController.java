package com.cuba.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuba.real.dao.PropertyDao;
import com.cuba.real.dto.PropertiesDto;
import com.cuba.real.dto.SearchDto;
import com.cuba.real.model.Property;

@RestController
public class PropertyController {

    @Autowired
    PropertyDao propertyDao;

    @CrossOrigin
    @RequestMapping("/properties")
    public PropertiesDto getProperties(SearchDto search, @RequestParam(required = false) String page) {
        return propertyDao.getProperties(search, page);
    }

    @CrossOrigin
    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public Property writeProperty(@RequestBody Property property) {
        return propertyDao.writeProperty(property);
    }
}
