package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(exclude = "medicineListDto")
public class MedicineDto {

    private Long id;

    @NotBlank(message = "Podaj nazwę lekarstwa")
    @Size(max = 240)
    private String name;

    @Min(0)
    @Max(2147483646)
    private double price;

    @ToString.Exclude
    private MedicineListDto medicineListDto;
}
