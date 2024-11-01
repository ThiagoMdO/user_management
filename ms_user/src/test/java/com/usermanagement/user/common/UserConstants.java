package com.usermanagement.user.common;

import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.out.UserResponseCreatedDTO;
import com.usermanagement.user.model.entities.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.usermanagement.user.common.RolesConstants.ROLE_ADM;
import static com.usermanagement.user.common.RolesConstants.ROLE_USER;

public class UserConstants {

    public static UserRequestCreateDTO USER_COMMON_01_REQUEST_01 = new UserRequestCreateDTO(
    "Manoel",
    "Sa",
    "111.222.444-99",
    LocalDate.of(1990, 4, 10),
    "manoel@email.com",
    "12345678",
    true,
    null
    );

    public static UserRequestCreateDTO USER_COMMON_02_REQUEST_02 = new UserRequestCreateDTO(
    "Fernanda",
    "Barboza",
    "222.485.444-98",
    LocalDate.of(1995, 4, 11),
    "fernanda@email.com",
    "12345678",
    true,
    new ArrayList<>(Arrays.asList(ROLE_ADM, ROLE_USER))
    );

    public static UserRequestCreateDTO USER_COMMON_03_REQUEST_03_CPF_ALREADY_IN_USE = new UserRequestCreateDTO(
    "Fernanda",
    "Barboza",
    "222.432.123-12",
    LocalDate.of(1995, 4, 11),
    "fernanda@email.com",
    "12345678",
    true,
    new ArrayList<>(Arrays.asList(ROLE_ADM, ROLE_USER))
    );

    public static UserRequestCreateDTO USER_COMMON_04_REQUEST_04_EMAIL_ALREADY_IN_USE = new UserRequestCreateDTO(
    "Fernanda",
    "Barboza",
    "222.485.444-98",
    LocalDate.of(1995, 4, 11),
    "mira@email.com",
    "12345678",
    true,
    new ArrayList<>(Arrays.asList(ROLE_ADM, ROLE_USER))
    );

    public static User USER_COMMON_01_REQUEST_01_CREATED_WITHOUT_ID = new User(
    "Manoel",
    "Sa",
    "111.222.444-99",
    LocalDate.of(1990, 4, 10),
    "manoel@email.com",
    "12345678",
    true,
    List.of(ROLE_USER)
    );

    public static User USER_COMMON_02_REQUEST_02_CREATED_WITHOUT_ID = new User(
    "Fernanda",
    "Barboza",
    "222.485.444-98",
    LocalDate.of(1995, 4, 11),
    "fernanda@email.com",
    "12345678",
    true,
    Arrays.asList(ROLE_ADM, ROLE_USER)
    );

    public static User USER_COMMON_01_REQUEST_01_CREATED = new User(
    UUID.randomUUID(),
    "Manoel",
    "Sa",
    "111.222.444-99",
    LocalDate.of(1990, 4, 10),
    "manoel@email.com",
    "12345678",
    true,
    List.of(ROLE_USER)
    );

    public static User USER_COMMON_02_REQUEST_02_CREATED = new User(
    UUID.randomUUID(),
    "Fernanda",
    "Barboza",
    "222.485.444-98",
    LocalDate.of(1995, 4, 11),
    "fernanda@email.com",
    "12345678",
    true,
    Arrays.asList(ROLE_ADM, ROLE_USER)
    );

    public static UserResponseCreatedDTO USER_COMMON_01_RESPONSE_01_CREATED = new UserResponseCreatedDTO(
    USER_COMMON_01_REQUEST_01_CREATED.getFirstName(),
    USER_COMMON_01_REQUEST_01_CREATED.getLastName(),
    USER_COMMON_01_REQUEST_01_CREATED.getCpf(),
    USER_COMMON_01_REQUEST_01_CREATED.getDate(),
    USER_COMMON_01_REQUEST_01_CREATED.getEmail(),
    USER_COMMON_01_REQUEST_01_CREATED.getRoles()
    );

    public static UserResponseCreatedDTO USER_COMMON_02_RESPONSE_02_CREATED = new UserResponseCreatedDTO(
    USER_COMMON_02_REQUEST_02_CREATED.getFirstName(),
    USER_COMMON_02_REQUEST_02_CREATED.getLastName(),
    USER_COMMON_02_REQUEST_02_CREATED.getCpf(),
    USER_COMMON_02_REQUEST_02_CREATED.getDate(),
    USER_COMMON_02_REQUEST_02_CREATED.getEmail(),
    USER_COMMON_02_REQUEST_02_CREATED.getRoles()
    );




    public static User USER_COMMON_IN_DB_01 = new User(
    UUID.randomUUID(),
    "Mira",
    "Mendes",
    "222.432.123-12",
    LocalDate.of(1999, 1, 22),
    "mira@email.com",
    "12345678",
    true,
    List.of(ROLE_USER)
    );

}
