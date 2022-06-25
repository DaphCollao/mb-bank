package com.mindHub.HomeBanking.event;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
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
        String senderEmail = "noreply.royaloak@gmail.com";
        String senderName = "Royal Oak staff";
        String subject = "Registration Confirmation";
        String confirmationUrl = "/registrationConfirm.html?token=" + token;

        String content = "<h2 style=\"color: black;\">\n Welcome <spa style=\"color: yellow;\"n>Client</spa>  to our MB bank Family!\n </h2>\n"
                +"<p style=\"color: #333533;\">\n We're so happy you decide to join us, please to verify your account open the link below\n </p>\n"
                +"<p> <a href=\"URL\">Click here to verify Email</a> </p>"
                +senderName;

        content= content.replace("Client", client.getFirstName().toUpperCase());
        content= content.replace("URL", "https://mb-bank.herokuapp.com/web"+confirmationUrl);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        helper.setFrom(senderEmail);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

}
