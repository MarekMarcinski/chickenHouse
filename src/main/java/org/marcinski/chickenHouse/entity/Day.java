package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "day_of_cycle")
@Data
@EqualsAndHashCode(exclude = "cycle")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    @Max(2147483646)
    @Column(name = "day_number")
    private int dayNumber;

    @Min(0)
    @Max(2147483646)
    @Column(name = "natural_downs")
    private int naturalDowns;

    @Min(0)
    @Max(2147483646)
    @Column(name = "selection_downs")
    private int selectionDowns;

    @Min(0)
    @Max(2147483646)
    @Column(name = "water_counter")
    private long waterCounter;

    @Min(0)
    @Max(15000)
    private int averageWeight;

    @Nullable
    @Size(max = 240)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    @ToString.Exclude
    private Cycle cycle;

    @OneToOne
    @JoinColumn(name = "forage_id")
    private Forage forage;

    @OneToOne
    @JoinColumn(name = "medicine_list_id")
    private MedicineList medicineList;
}