package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = "day")
public class Forage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    @Max(2147483646)
    private int quantity;

    @Min(0)
    @Max(2147483646)
    private double price;

    private LocalDate deliveryDate;

    @OneToOne(mappedBy = "forage")
    @ToString.Exclude
    private Day day;
}
