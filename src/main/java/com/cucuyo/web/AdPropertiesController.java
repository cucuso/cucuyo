package com.cucuyo.web;

import com.cucuyo.adapter.AdPropertiesServiceAdapter;
import com.cucuyo.core.domain.Page;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RestController("/properties")
public class AdPropertiesController {

    private final AdPropertiesServiceAdapter propertiesService;

    private final AdPropertiesSearchRequestMapper searchRequestMapper;

    private final AdPropertiesResourceMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AdPropertiesResource> search(AdPropertiesSearchRequest request) {
        val searchParams = searchRequestMapper.asAdPropertiesSearch(request);
        return propertiesService.search(searchParams).map(mapper::asAdPropertiesResource);
    }
}
