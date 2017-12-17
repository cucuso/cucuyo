package com.cucuyo.data;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface UUIDMapper {

    default String asString(UUID uuid) {
        return uuid.toString();
    }
}
