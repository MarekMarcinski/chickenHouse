package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.MedicineListDto;
import org.marcinski.chickenHouse.entity.MedicineList;

@Mapper(componentModel = "spring", uses = {MedicineMapper.class})
public interface MedicineListMapper {

    @Mapping(source = "dayDto", target = "day")
    @Mapping(source = "medicineDtos", target = "medicines")
    MedicineList mapTo (MedicineListDto medicineListDto);

    @Mapping(source = "day", target = "dayDto")
    @Mapping(source = "medicines", target = "medicineDtos")
    MedicineListDto mapTo (MedicineList medicineList);
}
