package org.example.printmanagement.model.services;

public interface IMailService {
    void sendEmailWithHTML(String to, String subject, String content);
}
