package org.marcinski.chickenHouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = "cycleDto")
public class SlaughterDto {

    private Long id;

    @NotNull(message = "Wprowadź datę!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfSlaughter;

    @Min(value = 0, message = "Ilość kurczaków nie może być mniejsza niż 0.")
    private Integer quantityOfChickens;

    @Min(value = 0, message = "Waga nie może być mniejsza niż 0.")
    private Double weightOfChickens;

    @Min(value = 0, message = "Cena nie może być mniejsza niż 0.")
    private Double sellingPrice;

    @ToString.Exclude
    private CycleDto cycleDto;

    private int fatteningDay;

    public double getAverageWeight(){
        return weightOfChickens/quantityOfChickens;
    }
}
