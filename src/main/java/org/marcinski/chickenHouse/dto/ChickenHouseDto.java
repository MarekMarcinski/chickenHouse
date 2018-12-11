package org.marcinski.chickenHouse.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class ChickenHouseDto {

    private Long id;

    @NotBlank(message = "Wprowadź nazwę kurnika")
    private String name;

    @Min(0)
    @Max(value = 2147483646, message = "Na pewno nie masz tak dużego kurnika ;)")
    private int areaOfHouse;

    private Set<CycleDto> cyclesDto;

    private UserDto userDto;
}
