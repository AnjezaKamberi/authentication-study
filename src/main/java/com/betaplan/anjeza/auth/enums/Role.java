package com.betaplan.anjeza.auth.enums;

public enum Role {
    // notice: roles should have as prefix 'ROLE_' so Spring Security can authorize correctly its clients
    ROLE_ADMIN, ROLE_USER
}
