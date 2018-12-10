package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = "dayDto")
public class ForageDto {

    private Long id;
    private String name;
    private int quantity;
    private double price;
    private LocalDate deliveryDate;

    @ToString.Exclude
    private DayDto dayDto;
}
