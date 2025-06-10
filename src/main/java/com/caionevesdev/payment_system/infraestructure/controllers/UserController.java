package com.caionevesdev.payment_system.infraestructure.controllers;

import com.caionevesdev.payment_system.application.services.UserService;
import com.caionevesdev.payment_system.domain.entity.UserEntity;
import com.caionevesdev.payment_system.infraestructure.dtos.user.UserRequestDTO;
import com.caionevesdev.payment_system.infraestructure.dtos.user.UserResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser (@RequestBody @Valid UserRequestDTO userRequestDTO) throws MessagingException, UnsupportedEncodingException {
        UserEntity user = userRequestDTO.toModel();

        UserResponseDTO newUser = userService.createUser(user);
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        boolean isVerified = userService.verify(code);

        if (isVerified) {
            return ResponseEntity.ok("User verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code or user already verified.");
        }
    }
}
