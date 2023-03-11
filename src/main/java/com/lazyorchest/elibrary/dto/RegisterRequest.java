package com.lazyorchest.elibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lazyorchest.elibrary.models.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;

    private String username;

    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
