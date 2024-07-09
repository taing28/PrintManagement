package org.example.printmanagement.model.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.printmanagement.model.entities.ConfirmEmail;
import org.example.printmanagement.model.entities.User;
import org.example.printmanagement.model.repositories.ConfirmEmailRepo;
import org.example.printmanagement.model.services.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;

@Transactional(rollbackFor = Exception.class)
@Service
public class MailService implements IMailService {
    @Autowired
    private ConfirmEmailRepo _emailRepo;

    private final JavaMailSender _mailSender;

    public MailService(JavaMailSender _mailSender) {
        this._mailSender = _mailSender;
    }

    @Override
    public void sendEmailWithHTML(String to, String subject, String content) {
        MimeMessage mimeMessage = _mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            _mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ConfirmEmail createConfirmMail(int userId, String code) {
        ConfirmEmail email = new ConfirmEmail();
        email.setUserId(userId);
        email.setUserMail(new User(userId));
        email.setConfirmCode(code);
        email.setExpiryTime(LocalDateTime.now().plusSeconds(60));
        email.setConfirm(false);
        return _emailRepo.save(email);
    }

    @Override
    public ConfirmEmail updateConfirmMail(String confirmCode) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(confirmCode);
        String decodedText = new String(decodedData);
        String[] splitedCode = decodedText.split("_");//[0]: id, [1]:randomStringCode
        try {
            int userId = Integer.parseInt(splitedCode[0]);
            ConfirmEmail confirmEmail = _emailRepo.findConfirmEmailByUserIdAndConfirmCode(userId, decodedText);
            //Check if existed
            if (confirmEmail == null) {
                throw new IllegalArgumentException("Confirmation email not found.");
            }
            //Check if confirmed
            if (confirmEmail.isConfirm()) {
                throw new IllegalStateException("Email already confirmed.");
            }
            //Check if expired
            if (confirmEmail.getExpiryTime().isAfter(LocalDateTime.now())) {
                confirmEmail.setConfirm(true);
                _emailRepo.save(confirmEmail);
                return confirmEmail;
            } else {
                throw new IllegalArgumentException("Confirmation email has expired.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return null;
    }
}
