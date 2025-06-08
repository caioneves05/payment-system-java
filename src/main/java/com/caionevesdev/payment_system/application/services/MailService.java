package com.caionevesdev.payment_system.application.services;

import com.caionevesdev.payment_system.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private String verifyURL = "http://localhost:8080/users/verify";

    public void sendVerificationEmail(UserEntity user, String verificationCode) {
        String toAddress = user.getEmail();
        String senderName = "Payment System";
        String fromAddress = "paymentsystem@gmail.com";
        String subject = "Please verify your registration";
        String content = "<p>Dear " + user.getFullname() + ",</p>"
                + "<p>Thank you for registering with us. Please click the link below to verify your registration:</p>"
                + "<p><a href=\"" + verifyURL + "?code=" + verificationCode + "\">Verify Now</a></p>"
                + "<p>Thank you!</p>";
    }
}
