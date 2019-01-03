package org.marcinski.chickenHouse.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class CycleDto {

    private Long id;

    @NotNull(message = "Wprowadź liczbę kurczaków.")
    @Min(0)
    @Max(value = 2147483646, message = "Nie stać cię na tyle ;)")
    private Integer numberOfChickens;

    @NotNull(message = "Wprowadź datę!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;

    private String hybrid;
    private String hatchery;

    @Min(0)
    @Max(2147483646)
    private Double price;

    private boolean completed;

    private Set<DayDto> daysDto;

    private ChickenHouseDto chickenHouseDto;

    private Set<SlaughterDto> slaughterDtos;
}
