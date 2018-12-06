package org.marcinski.chickenHouse.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDto {

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

    private RoleDto roleDto;

    private Set<ChickenHouseDto> chickenHouseDtos;
}
