package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.DayDto;
import org.marcinski.chickenHouse.entity.Day;

@Mapper(componentModel = "spring", uses = {ForageMapper.class, MedicineMapper.class})
public interface DayMapper {

    @Mapping(source = "cycleDto", target = "cycle")
    @Mapping(source = "forageDto", target = "forage")
    @Mapping(source = "medicineDtos", target = "medicines")
    Day mapTo(DayDto dayDto);

    @Mapping(source = "cycle", target = "cycleDto")
    @Mapping(source = "forage", target = "forageDto")
    @Mapping(source = "medicines", target = "medicineDtos")
    DayDto mapTo(Day day);
}
