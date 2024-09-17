package com.buccodev.auth_api.dto;

import com.buccodev.auth_api.enuns.Role;

public record UserDTO(String userName, String email, String password, Role role) {
}
