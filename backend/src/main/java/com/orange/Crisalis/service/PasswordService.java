package com.orange.Crisalis.service;

import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public PasswordService(JavaMailSender javaMailSender, PasswordEncoder passwordEncoder,UserService userService) {
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public String generateRandomPassword() {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    public void sendPasswordByEmail(String userEmail) {
        String randomPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(randomPassword);

        // almacenar la contraseña en tu base de datos con el usuario correspondiente
        UserEntity user =  userService.getByEmail(userEmail).orElseThrow(null);
        user.setPassword(encodedPassword);
        userService.save(user);

        // Envía la contraseña por correo electrónico
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Contraseña Aleatoria Generada");
        mailMessage.setText("Tu nueva contraseña es: " + randomPassword);

        javaMailSender.send(mailMessage);
    }
}