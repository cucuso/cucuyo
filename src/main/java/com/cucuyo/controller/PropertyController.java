package com.cucuyo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cucuyo.dao.PropertyDao;
import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.model.Property;

@RestController
public class PropertyController {

    @Autowired
    PropertyDao propertyDao;

    @GetMapping("/properties")
    public PropertiesDto getProperties(SearchDto search, @RequestParam(required = false) String page) {
        return propertyDao.getProperties(search, page);
    }

    @PostMapping(value = "/properties")
    public Property writeProperty(@RequestBody Property property) {
        return propertyDao.writeProperty(property);
    }
}
