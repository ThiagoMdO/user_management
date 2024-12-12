package com.usermanagement.user.common;

import com.usermanagement.user.model.dto.in.UserRequestCreateDTO;
import com.usermanagement.user.model.dto.in.UserRequestUpdateDTO;
import com.usermanagement.user.model.dto.out.UserResponseDTO;
import com.usermanagement.user.model.dto.out.UserUpdatedResponseDTO;
import com.usermanagement.user.model.entities.User;

import java.time.LocalDate;
import java.util.*;

import static com.usermanagement.user.common.RolesConstants.ROLE_ADM;
import static com.usermanagement.user.common.RolesConstants.ROLE_USER;

public class UserConstants {

    public static UserRequestCreateDTO USER_COMMON_01_INVALID_REQUEST_01 = new UserRequestCreateDTO(
            "A",
            "S",
            "",
            null,
            null,
            null,
            true,
            null
    );

    public static UserRequestCreateDTO USER_COMMON_01_REQUEST_01 = new UserRequestCreateDTO(
            "Manoel",
            "Sa",
            "004.776.410-40",
            LocalDate.of(1990, 4, 10),
            "manoel@email.com",
            "12345678",
            true,
            null
    );

    public static UserRequestCreateDTO USER_COMMON_02_REQUEST_02 = new UserRequestCreateDTO(
            "Fernanda",
            "Barboza",
            "571.134.160-04",
            LocalDate.of(1995, 4, 11),
            "fernanda@email.com",
            "12345678",
            true,
            new ArrayList<>(Arrays.asList(ROLE_ADM, ROLE_USER))
    );

    public static UserRequestCreateDTO USER_COMMON_03_REQUEST_03_CPF_ALREADY_IN_USE = new UserRequestCreateDTO(
            "Fernanda",
            "Barboza",
            "571.134.160-04",
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
            "004.776.410-40",
            LocalDate.of(1990, 4, 10),
            "manoel@email.com",
            "12345678",
            true,
            List.of(ROLE_USER)
    );

    public static User USER_COMMON_02_REQUEST_02_CREATED_WITHOUT_ID = new User(
            "Fernanda",
            "Barboza",
            "571.134.160-04",
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
            "004.776.410-40",
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

    public static UserResponseDTO USER_COMMON_01_RESPONSE_01_CREATED = new UserResponseDTO(
            USER_COMMON_01_REQUEST_01_CREATED.getFirstName(),
            USER_COMMON_01_REQUEST_01_CREATED.getLastName(),
            USER_COMMON_01_REQUEST_01_CREATED.getCpf(),
            USER_COMMON_01_REQUEST_01_CREATED.getDate(),
            USER_COMMON_01_REQUEST_01_CREATED.getEmail(),
            USER_COMMON_01_REQUEST_01_CREATED.getRoles()
    );

    public static UserResponseDTO USER_COMMON_02_RESPONSE_02_CREATED = new UserResponseDTO(
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

    public static User USER_COMMON_IN_DB_02 = new User(
        UUID.randomUUID(),
        "Raisa",
        "Goncalves",
        "897.282.200-06",
        LocalDate.of(1999, 1, 22),
        "raiG@email.com",
        "12345678",
        true,
        List.of(ROLE_USER)
    );

    public static User USER_DISABLE_COMMON_IN_DB_01 = new User(
            UUID.randomUUID(),
            "Solange",
            "Souza",
            "111.222.123-00",
            LocalDate.of(1999, 1, 22),
            "sol@email.com",
            "12345678",
            false,
            List.of(ROLE_USER)
    );

    public static UserRequestUpdateDTO USER_COMMON_REQUEST_TO_UPDATE = new UserRequestUpdateDTO(
            Optional.of("Paula"),
            Optional.empty(),
            Optional.of(LocalDate.parse("1993-09-22")),
            Optional.of("f1s@email.com"),
            Optional.of(true)
    );

    public static UserRequestUpdateDTO USER_COMMON02_REQUEST_TO_UPDATE_WITH_EMAIL_ALREADY_IN_USE = new UserRequestUpdateDTO(
    Optional.empty(),
    Optional.empty(),
        Optional.empty(),
            Optional.of("mira@email.com"),
            Optional.of(true)
    );

    public static UserRequestUpdateDTO USER_COMMON_REQUEST_WITH_SOME_FIELDS_VOID_TO_UPDATE = new UserRequestUpdateDTO(
    Optional.empty(),
    Optional.empty(),
        Optional.empty(),
            Optional.of("test@email.com"),
            Optional.of(true)
    );

    public static UserRequestUpdateDTO USER_COMMON_REQUEST_TO_UPDATE_WITH_EMAIL_SAME_SECTION_USER = new UserRequestUpdateDTO(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of("mira@email.com"),
            Optional.of(true)
    );

    public static UserRequestUpdateDTO USER_COMMON_REQUEST_TO_UPDATE_WITH_EMPTY_EMAIL = new UserRequestUpdateDTO(
            Optional.empty(),
            Optional.of("Oliveira"),
            Optional.empty(),
            Optional.empty(),
            Optional.of(true)
    );

    public static User USER_COMMON_UPLOADED_IN_DB = User.builder()
            .id(USER_COMMON_IN_DB_01.getId())
            .firstName(USER_COMMON_REQUEST_TO_UPDATE.firstName().get())
            .lastName(USER_COMMON_IN_DB_01.getLastName())
            .cpf(USER_COMMON_IN_DB_01.getCpf())
            .date(USER_COMMON_REQUEST_TO_UPDATE.date().get())
            .email(USER_COMMON_REQUEST_TO_UPDATE.email().get())
            .password(USER_COMMON_IN_DB_01.getPassword())
            .active(USER_COMMON_REQUEST_TO_UPDATE.active().get())
            .roles(USER_COMMON_IN_DB_01.getRoles())
            .build();

    public static User USER_COMMON_UPLOADED_IN_DB_WITH_SOME_FIELDS_VOID = User.builder()
            .id(USER_COMMON_IN_DB_01.getId())
            .firstName(USER_COMMON_IN_DB_01.getFirstName())
            .lastName(USER_COMMON_IN_DB_01.getLastName())
            .cpf(USER_COMMON_IN_DB_01.getCpf())
            .date(USER_COMMON_IN_DB_01.getDate())
            .email(USER_COMMON_REQUEST_WITH_SOME_FIELDS_VOID_TO_UPDATE.email().get())
            .password(USER_COMMON_IN_DB_01.getPassword())
            .active(USER_COMMON_REQUEST_WITH_SOME_FIELDS_VOID_TO_UPDATE.active().get())
            .roles(USER_COMMON_IN_DB_01.getRoles())
            .build();

    public static User USER_COMMON_UPLOADED_IN_DB_WITH_EMAIL_SAME_SECTION_USER = User.builder()
            .id(USER_COMMON_IN_DB_01.getId())
            .firstName(USER_COMMON_IN_DB_01.getFirstName())
            .lastName(USER_COMMON_IN_DB_01.getLastName())
            .cpf(USER_COMMON_IN_DB_01.getCpf())
            .date(USER_COMMON_IN_DB_01.getDate())
            .email(USER_COMMON_REQUEST_TO_UPDATE_WITH_EMAIL_SAME_SECTION_USER.email().get())
            .password(USER_COMMON_IN_DB_01.getPassword())
            .active(USER_COMMON_REQUEST_TO_UPDATE_WITH_EMAIL_SAME_SECTION_USER.active().get())
            .roles(USER_COMMON_IN_DB_01.getRoles())
            .build();

    public static User USER_COMMON_UPLOADED_IN_DB_WITH_OTHER_LASTNAME_FROM_EMPTY_EMAIL = User.builder()
            .id(USER_COMMON_IN_DB_01.getId())
            .firstName(USER_COMMON_IN_DB_01.getFirstName())
            .lastName(USER_COMMON_REQUEST_TO_UPDATE_WITH_EMPTY_EMAIL.lastName().get())
            .cpf(USER_COMMON_IN_DB_01.getCpf())
            .date(USER_COMMON_IN_DB_01.getDate())
            .email(USER_COMMON_IN_DB_01.getEmail())
            .password(USER_COMMON_IN_DB_01.getPassword())
            .active(USER_COMMON_IN_DB_01.isActive())
            .roles(USER_COMMON_IN_DB_01.getRoles())
            .build();

    public static UserUpdatedResponseDTO USER_COMMON_RESPONSE_UPDATED =
            UserUpdatedResponseDTO.createDTO(USER_COMMON_UPLOADED_IN_DB);

    public static UserUpdatedResponseDTO USER_COMMON_RESPONSE_UPDATED_WITH_SOME_FIELDS_VOID =
            UserUpdatedResponseDTO.createDTO(USER_COMMON_UPLOADED_IN_DB_WITH_SOME_FIELDS_VOID);

    public static UserUpdatedResponseDTO USER_COMMON_RESPONSE_UPDATED_WITH_EMAIL_SAME_SECTION_USER =
            UserUpdatedResponseDTO.createDTO(USER_COMMON_UPLOADED_IN_DB_WITH_EMAIL_SAME_SECTION_USER);

    public static UserUpdatedResponseDTO USER_COMMON_RESPONSE_UPDATED_WITH_EMPTY_EMAIL =
            UserUpdatedResponseDTO.createDTO(USER_COMMON_UPLOADED_IN_DB_WITH_OTHER_LASTNAME_FROM_EMPTY_EMAIL);


}
