package com.usermanagement.user.repositories;

import com.usermanagement.user.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(String email);
}
