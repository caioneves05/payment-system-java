package com.caionevesdev.payment_system.application.services;

import com.caionevesdev.payment_system.domain.entity.UserEntity;
import com.caionevesdev.payment_system.domain.repository.UserRepository;
import com.caionevesdev.payment_system.infraestructure.dtos.user.UserResponseDTO;
import com.caionevesdev.payment_system.utils.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService emailService;

    private PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserEntity user) throws MessagingException, UnsupportedEncodingException {

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

        UserResponseDTO savedUser = new UserResponseDTO(
                newUser.getId(),
                newUser.getFullname(),
                newUser.getEmail(),
                newUser.getPassword()
        );

        emailService.sendVerificationEmail(newUser, randomCode);

        return savedUser;
    }

    public boolean verify(String verificationCode) {
        UserEntity user = userRepository.findByVerificationCode(verificationCode);

        if(user == null || user.isEnabled()) {
            return false;
        }

        user.setVerificationCode(null);
        user.setEnabled(true);

        userRepository.save(user);

        return true;
    }
}
