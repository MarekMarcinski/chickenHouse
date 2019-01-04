package org.marcinski.chickenHouse.service;

public interface EmailService {

    void sendEmailWithAuthorizationLink(String to, String uuid);

    void sendEmailWithResetPassword(String to, String newPass);
}
