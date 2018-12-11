package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "day_of_cycle")
@Data
@EqualsAndHashCode(exclude = "cycle")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Column(name = "day_number")
    private int dayNumber;

    @Nullable
    @Column(name = "natural_downs")
    private int naturalDowns;

    @Nullable
    @Column(name = "selection_downs")
    private int selectionDowns;

    @Nullable
    @Column(name = "water_counter")
    private long waterCounter;

    @Nullable
    private int averageWeight;

    @Nullable
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