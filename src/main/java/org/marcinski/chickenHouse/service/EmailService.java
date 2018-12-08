package org.marcinski.chickenHouse.service;

public interface EmailService {

    void sendEmailWithAuthorizationLink(String to, String uuid);
}
