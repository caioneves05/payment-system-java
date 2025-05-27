package com.caionevesdev.payment_system.application.services;

import com.caionevesdev.payment_system.domain.entity.UserEntity;
import com.caionevesdev.payment_system.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserEntity createUser(UserEntity user) {

        UserDetails hasThisUser = userRepository.findByEmail(user.getEmail());

        if(hasThisUser.getUsername() != null){
            throw new RuntimeException("User already exist!");
        }

        String encondedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encondedPassword);

        return userRepository.save(user);
    }
}
