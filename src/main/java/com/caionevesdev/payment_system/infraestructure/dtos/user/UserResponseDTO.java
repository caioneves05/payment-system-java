package com.caionevesdev.payment_system.infraestructure.dtos.user;

public record UserResponseDTO(
        Long id,
        String fullname,
        String email,
        String password
) {}
