package com.cucuyo.web;

import com.cucuyo.adapter.AdPropertiesServiceAdapter;
import com.cucuyo.core.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController("/properties")
public class AdPropertiesController {

    private AdPropertiesServiceAdapter propertiesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AdPropertiesResource> search() {
        return null;
    }
}
