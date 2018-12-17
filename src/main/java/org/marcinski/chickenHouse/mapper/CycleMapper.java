package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.entity.Cycle;

@Mapper(componentModel = "spring", uses = {DayMapper.class, SlaughterMapper.class})
public interface CycleMapper {

    @Mapping(source = "chickenHouseDto", target = "chickenHouse")
    @Mapping(source = "daysDto", target = "days")
    @Mapping(source = "slaughterDtos", target = "slaughters")
    Cycle mapTo(CycleDto cycleDto);

    @Mapping(source = "chickenHouse", target = "chickenHouseDto")
    @Mapping(source = "days", target = "daysDto")
    @Mapping(source = "slaughters", target = "slaughterDtos")
    CycleDto mapTo(Cycle cycle);
}
