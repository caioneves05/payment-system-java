package com.caionevesdev.payment_system.infraestructure.dtos.user;

import com.caionevesdev.payment_system.domain.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotNull(message = "FullName is required")
        @NotBlank(message = "FullName cannot be blank")
        @Size(min = 3, message = "FullName must be at least 3 characters long")
        String fullname,

        @NotNull(message = "Email is required")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Password is required")
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
)
{
    public UserEntity toModel() {
        return new UserEntity(fullname, email, password);
    }
}
