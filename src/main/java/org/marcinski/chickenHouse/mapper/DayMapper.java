package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.DayDto;
import org.marcinski.chickenHouse.entity.Day;

@Mapper(componentModel = "spring", uses = {ForageMapper.class, MedicineListMapper.class})
public interface DayMapper {

    @Mapping(source = "cycleDto", target = "cycle")
    @Mapping(source = "forageDto", target = "forage")
    @Mapping(source = "medicineListDto", target = "medicineList")
    Day mapTo(DayDto dayDto);

    @Mapping(source = "cycle", target = "cycleDto")
    @Mapping(source = "forage", target = "forageDto")
    @Mapping(source = "medicineList", target = "medicineListDto")
    @Mapping(target = "dayDate", ignore = true)
    DayDto mapTo(Day day);
}
