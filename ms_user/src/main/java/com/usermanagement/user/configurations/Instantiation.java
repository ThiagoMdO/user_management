package com.usermanagement.user.configurations;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.model.entities.Role;
import com.usermanagement.user.model.entities.User;
import com.usermanagement.user.repositories.RoleRepository;
import com.usermanagement.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class Instantiation implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role adm = new Role(UUID.randomUUID(), UserRolesEnum.ADM_USER, null);
        Role common = new Role(UUID.randomUUID(), UserRolesEnum.COMMON_USER, null);

        roleRepository.save(adm);
        roleRepository.save(common);

        List<Role> rolesUser1 = Arrays.asList(adm, common);
        List<Role> rolesUser2 = List.of(common);

        String passwordEncrypted = new BCryptPasswordEncoder().encode("12345678");
        User user1 = User.builder()
        .id(UUID.randomUUID())
        .firstName("Ronaldo")
        .lastName("Da Silva")
        .cpf("123.456.788-90")
        .date(LocalDate.now())
        .email("Rsilva@email.com")
        .password(passwordEncrypted)
        .active(true)
        .roles(rolesUser1)
        .build();

        String passwordEncrypted2 = new BCryptPasswordEncoder().encode("12345678");
        User user2 = User.builder()
        .id(UUID.randomUUID())
        .firstName("Silvana")
        .lastName("Bezerra")
        .cpf("111.222.787-90")
        .date(LocalDate.now())
        .email("silvana@email.com")
        .password(passwordEncrypted2)
        .active(true)
        .roles(rolesUser2)
        .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
