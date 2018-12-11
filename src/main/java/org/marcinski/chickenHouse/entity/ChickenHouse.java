package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "chicken_house")
@Data
public class ChickenHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    @NotBlank(message = "Please provide a name of chicken house")
    private String name;

    @Min(0)
    @Max(2147483646)
    @Column(name = "area")
    private int areaOfHouse;

    @OneToMany(mappedBy = "chickenHouse", cascade = CascadeType.REMOVE)
    private Set<Cycle> cycles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
