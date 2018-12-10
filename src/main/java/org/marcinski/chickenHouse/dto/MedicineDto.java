package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(exclude = "dayDto")
public class MedicineDto {

    private Long id;

    @NotNull
    private String name;
    private String price;

    @ToString.Exclude
    private DayDto dayDto;
}
