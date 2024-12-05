package com.usermanagement.user.model.entities;

import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "user_system")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 3)
    private String firstName;

    @Size(min = 3)
    private String lastName;

    @Column(unique = true)
    @CPF
    private String cpf;

    @NotNull(message = "The date can't be null")
    private LocalDate date;

    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    private boolean active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public User(String firstName,
                String lastName,
                String cpf,
                LocalDate date,
                String email,
                String password,
                boolean active,
                List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.date = date;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public static User create(UserRequestCreateDTO requestCreateDTO) {

        return new User(
            requestCreateDTO.firstName(),
            requestCreateDTO.lastName(),
            requestCreateDTO.cpf(),
            requestCreateDTO.date(),
            requestCreateDTO.email(),
            requestCreateDTO.password(),
            requestCreateDTO.active(),
            requestCreateDTO.roles()
        );
    }

}
