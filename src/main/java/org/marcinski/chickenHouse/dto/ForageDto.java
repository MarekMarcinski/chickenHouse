package org.marcinski.chickenHouse.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ForageDto {

    private Long id;
    private String name;
    private int quantity;
    private double price;
    private LocalDate deliveryDate;
}
