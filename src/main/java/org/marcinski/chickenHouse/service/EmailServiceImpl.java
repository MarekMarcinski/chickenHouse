package org.marcinski.chickenHouse.service;

import org.apache.tomcat.util.codec.binary.Base64;
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
    public void sendEmailWithAuthorizationLink(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        String encodedMail = Base64.encodeBase64String(to.getBytes());
        message.setTo(to);
        message.setSubject("Aktywacja konta");
        message.setText("Witaj, aby aktywować konto wejdź w link: " +
                "localhost:8080/registration/" + encodedMail);
        mailSender.send(message);
    }
}
