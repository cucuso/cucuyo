package com.cucuyo.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public final class PageRequest {

    private final String offset;

    @Getter
    private final int limit;

    public Optional<String> getOffset() {
        return Optional.ofNullable(offset);
    }
}
