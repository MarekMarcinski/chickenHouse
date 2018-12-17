package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(exclude = "cycle")
public class Slaughter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfSlaughter;

    @Min(0)
    private int quantityOfChickens;
    @Min(0)
    private double weightOfChickens;

    @Min(0)
    private double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    @ToString.Exclude
    private Cycle cycle;
}
