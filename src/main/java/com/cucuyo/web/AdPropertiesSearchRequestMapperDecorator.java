package com.cucuyo.web;

import com.cucuyo.core.domain.AdPropertiesSearch;
import com.cucuyo.core.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public abstract class AdPropertiesSearchRequestMapperDecorator implements AdPropertiesSearchRequestMapper {

    private final AdPropertiesSearchRequestMapper delegate;

    @Override
    public AdPropertiesSearch asAdPropertiesSearch(AdPropertiesSearchRequest request) {
        val pageRequest = new PageRequest(request.getOffset(), request.getLimit());
        val searchParams = delegate.asAdPropertiesSearch(request);
        searchParams.setPageRequest(pageRequest);
        return searchParams;
    }
}
