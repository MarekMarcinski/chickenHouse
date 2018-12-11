package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "dayDto")
public class MedicineListDto {

    private Long id;

    private LocalDate applicationDate;

    private Set<MedicineDto> medicineDtos;

    @ToString.Exclude
    private DayDto dayDto;
}
