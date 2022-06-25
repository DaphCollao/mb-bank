package com.mindHub.HomeBanking.event;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.models.VerificationToken;
import com.mindHub.HomeBanking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) throws MessagingException, UnsupportedEncodingException {
        Client client = event.getClient();
        String token = client.getToken();

        String recipientEmail = client.getEmail();
        String senderEmail = "royaloaktest@outlook.com";
        String senderName = "Royal Oak staff";
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        String content = "<h1> Test </h1> <p> ClientName </p> <p> <a href=\"URL\"> Click aquí </a> </p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(senderEmail, senderName);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText("http://localhost:8080" + confirmationUrl);
        mailSender.send(message);
    }

}
