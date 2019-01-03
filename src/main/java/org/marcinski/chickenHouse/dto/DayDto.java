package org.marcinski.chickenHouse.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class DayDto {

    private Long id;

    @NotNull
    @Min(0)
    @Max(240)
    private int dayNumber;

    @Min(0)
    @Max(2147483646)
    private Integer naturalDowns;

    @Min(0)
    @Max(2147483646)
    private Integer selectionDowns;

    @Min(0)
    @Max(2147483646)
    private Long waterCounter;

    @Min(0)
    @Max(value = 15000, message = "Daj spokój, przecież to tylko kurczak. Na pewno tyle nie waży ;)")
    private Integer averageWeight;

    @Nullable
    @Size(max = 240)
    private String comments;

    private LocalDate dayDate;

    private CycleDto cycleDto;

    private ForageDto forageDto;

    private MedicineListDto medicineListDto;

    public double getAllDowns(){
        return naturalDowns + selectionDowns;
    }
}
