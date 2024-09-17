package com.buccodev.auth_api.enuns;

public enum Role {

    ADMIN("admin"),
    USER("user");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
