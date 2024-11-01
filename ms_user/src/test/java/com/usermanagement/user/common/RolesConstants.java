package com.usermanagement.user.common;

import com.usermanagement.user.enums.UserRolesEnum;
import com.usermanagement.user.model.entities.Role;

import java.util.Arrays;
import java.util.UUID;

import static com.usermanagement.user.common.UserConstants.USER_COMMON_IN_DB_01;

public class RolesConstants {

    public static Role ROLE_USER = new Role(
    UUID.randomUUID(),
    UserRolesEnum.COMMON_USER,
    Arrays.asList(USER_COMMON_IN_DB_01)
    );

    public static Role ROLE_ADM = new Role(
    UUID.randomUUID(),
    UserRolesEnum.ADM_USER,
    Arrays.asList()
    );
}
