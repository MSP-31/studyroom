package com.synclife.studyroom.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum UserRoleType {
    ADMIN("ADMIN"),
    USER("USER");

    @Getter
    private final String role;

    UserRoleType(String role) {
        this.role = role;
    }

    @JsonCreator
    public static UserRoleType from(String role) {
        for (UserRoleType userRole : UserRoleType.values()) {
            if (userRole.getRole().equals(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }

    @JsonValue
    public String getValue() {
        return role;
    }
}
