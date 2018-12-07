package org.marcinski.chickenHouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(exclude = "chickenHouses")
@ToString(exclude = "chickenHouses")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email(message = "Wpisz e-mail poprawnie!")
    @NotEmpty(message = "Wpisz e-mail!")
    private String email;

    @NotEmpty(message = "Wpisz hasło!")
    @Length(min = 5, message = "Hasło musi zawierać przynajmniej 5 znaków!")
    private String password;

    @NotEmpty(message ="Wpisz nazwę użytkownika!")
    private String name;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<ChickenHouse> chickenHouses;
}
