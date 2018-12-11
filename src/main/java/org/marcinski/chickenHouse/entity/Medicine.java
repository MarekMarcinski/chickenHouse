package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode(exclude = "medicineList")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 240)
    private String name;

    @Min(0)
    @Max(2147483646)
    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_list_id")
    @ToString.Exclude
    private MedicineList medicineList;
}
