package com.usermanagement.user.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usermanagement.user.enums.UserRolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID id;

    @Enumerated(EnumType.STRING)
    private UserRolesEnum typeRole;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return typeRole == role.typeRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeRole);
    }
}

