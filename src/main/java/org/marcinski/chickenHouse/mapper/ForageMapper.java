package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.ForageDto;
import org.marcinski.chickenHouse.entity.Forage;

@Mapper(componentModel = "spring")
public interface ForageMapper {

    @Mapping(source = "dayDto", target = "day")
    Forage mapTo(ForageDto dayDto);

    @Mapping(source = "day", target = "dayDto")
    ForageDto mapTo(Forage day);
}
