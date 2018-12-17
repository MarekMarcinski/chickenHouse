package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "cycle")
@Data
@EqualsAndHashCode(exclude = "chickenHouse")
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cycle_id")
    private Long id;

    @NotNull(message = "Please provide number of chickens!")
    @Min(0)
    @Max(2147483646)
    @Column(name = "number_of_chickens")
    private int numberOfChickens;

    @NotNull(message = "Please provide a start day!")
    @Column(name = "start_day")
    private LocalDate startDay;

    @Nullable
    private String hybrid;
    @Nullable
    private String hatchery;

    @Min(0)
    @Max(2147483646)
    private double price;

    private boolean completed;

    @OneToMany(mappedBy = "cycle", cascade = CascadeType.REMOVE)
    private Set<Day> days;

    @ManyToOne
    @JoinColumn(name = "house_id")
    @ToString.Exclude
    private ChickenHouse chickenHouse;

    @OneToMany(mappedBy = "cycle")
    private Set<Slaughter> slaughters;
}
