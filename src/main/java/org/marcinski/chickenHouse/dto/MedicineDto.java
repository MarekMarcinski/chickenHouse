package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "medicineListDto")
public class MedicineDto {

    private Long id;

    private String name;
    private double price;

    @ToString.Exclude
    private MedicineListDto medicineListDto;
}
