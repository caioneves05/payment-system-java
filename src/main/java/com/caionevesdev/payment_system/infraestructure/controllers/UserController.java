package com.caionevesdev.payment_system.infraestructure.controllers;

import com.caionevesdev.payment_system.application.services.UserService;
import com.caionevesdev.payment_system.domain.entity.UserEntity;
import com.caionevesdev.payment_system.infraestructure.dtos.user.UserRequestDTO;
import com.caionevesdev.payment_system.infraestructure.dtos.user.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser (@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserEntity user = userRequestDTO.toModel();

        UserResponseDTO newUser = userService.createUser(user);
        return ResponseEntity.ok().body(newUser);
    }
}
