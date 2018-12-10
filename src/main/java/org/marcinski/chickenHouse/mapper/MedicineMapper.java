package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.MedicineDto;
import org.marcinski.chickenHouse.entity.Medicine;

@Mapper(componentModel = "spring")
public interface MedicineMapper {

    @Mapping(source = "dayDto", target = "day")
    Medicine mapTo (MedicineDto medicineDto);

    @Mapping(source = "day", target = "dayDto")
    MedicineDto mapTo (Medicine medicine);
}
