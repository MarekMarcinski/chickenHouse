package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.entity.Slaughter;

@Mapper(componentModel = "spring")
public interface SlaughterMapper {

    @Mapping(source = "cycleDto", target = "cycle")
    Slaughter mapTo(SlaughterDto slaughterDto);

    @Mapping(source = "cycle", target = "cycleDto")
    @Mapping(target = "fatteningDay", ignore = true)
    SlaughterDto mapTo(Slaughter slaughter);
}
