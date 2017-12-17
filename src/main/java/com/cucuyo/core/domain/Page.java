package com.cucuyo.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public final class Page<T> {

    @Getter
    private final long totalElements;

    @Getter
    private final String nextPage;

    @Getter
    private final List<T> content;

    public <S> Page<S> map(Function<T, S> converter) {
        val mappedConent = content
                .stream()
                .map(converter)
                .collect(toList());
        return new Page<>(totalElements, nextPage, mappedConent);
    }
}
