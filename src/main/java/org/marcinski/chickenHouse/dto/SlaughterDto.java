package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = "cycleDto")
public class SlaughterDto {

    private Long id;

    private LocalDate dateOfSlaughter;

    @Min(0)
    private int quantityOfChickens;

    @Min(0)
    private double weightOfChickens;

    @Min(0)
    private double sellingPrice;

    @ToString.Exclude
    private CycleDto cycleDto;
}
