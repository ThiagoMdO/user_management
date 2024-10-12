package com.usermanagement.user.repositories;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByTypeRole(UserRolesEnum name);
}
