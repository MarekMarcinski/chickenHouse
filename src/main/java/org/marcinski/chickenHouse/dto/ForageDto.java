package org.marcinski.chickenHouse.dto;

import lombok.Data;

@Data
public class ForageDto {

    private Long id;
    private String name;
    private int quantity;
    private double price;
}
