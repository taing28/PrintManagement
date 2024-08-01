package org.example.printmanagement.model.services;

import org.example.printmanagement.model.entities.ConfirmEmail;

public interface IMailService {
    void sendEmailWithHTML(String to, String subject, String content);

    ConfirmEmail createConfirmMail(int userId, String code);

    ConfirmEmail updateConfirmMail(String confirmCode) throws Exception;
}
