package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = "dayDto")
public class ForageDto {

    private Long id;

    @NotBlank(message = "Podaj nazwę paszy!")
    @Size(max = 240)
    private String name;

    @Min(0)
    @Max(2147483646)
    private int quantity;

    @Min(0)
    @Max(2147483646)
    private double price;

    private LocalDate deliveryDate;

    @ToString.Exclude
    private DayDto dayDto;
}
