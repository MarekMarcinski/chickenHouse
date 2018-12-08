package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.UserDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailWithAuthorizationLink(String to, String uuid) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Aktywacja konta");
        message.setText("Witaj, aby aktywować konto wejdź w link: " +
                "https://localhost:8443/registration/" + uuid);
        mailSender.send(message);
    }
}
