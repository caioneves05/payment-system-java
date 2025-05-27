package com.caionevesdev.payment_system.domain.repository;

import com.caionevesdev.payment_system.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserDetails findByEmail(String email);
}
