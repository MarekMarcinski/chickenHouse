package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "day")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String price;


    @ManyToOne
    @JoinColumn(name = "day_id")
    @ToString.Exclude
    private Day day;
}
