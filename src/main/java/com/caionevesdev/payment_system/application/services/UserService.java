package com.caionevesdev.payment_system.application.services;

import com.caionevesdev.payment_system.domain.entity.UserEntity;
import com.caionevesdev.payment_system.domain.repository.UserRepository;
import com.caionevesdev.payment_system.infraestructure.dtos.user.UserResponseDTO;
import com.caionevesdev.payment_system.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserEntity user) {

        UserDetails hasThisUser = userRepository.findByEmail(user.getEmail());

        if(hasThisUser.getUsername() != null){
            throw new RuntimeException("User already exist!");
        }

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        String randomCode = RandomString.generateRandomString(64);

        user.setVerificationCode(randomCode);
        user.setActive(false);

        UserEntity newUser = userRepository.save(user);

        return new UserResponseDTO(
                newUser.getId(),
                newUser.getFullname(),
                newUser.getEmail(),
                newUser.getPassword()
        );
    }
}
