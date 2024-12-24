package com.RestApiExample.demo.dto;

import com.RestApiExample.demo.models.Role;
import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private Role role;
}
