package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "day")
public class MedicineList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate applicationDate;

    @OneToMany(mappedBy = "medicineList", cascade = CascadeType.REMOVE)
    private Set<Medicine> medicines;

    @OneToOne(mappedBy = "medicineList", cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    private Day day;
}
