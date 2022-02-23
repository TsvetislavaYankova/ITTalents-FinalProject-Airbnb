package com.example.ittalentsfinalprojectairbnb.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${mail.auth}")
    private String mailAuth;
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.ttl}")
    private String ttlEnabled;
    @Value("${mail.senderEmail}")
    private String senderEmail;
    @Value("${mail.senderPassword}")
    private String senderPassword;

    public void sendEmail(String recipient, String subject, String msg) {
        Message message = prepareMessage(recipient, subject, msg);
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Email sending failed - " + e.getMessage());
        }
    }

    private Message prepareMessage(String recipient, String subject, String msg) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties properties = new Properties();
        properties.put(mailAuth, true);
        properties.put(ttlEnabled, true);
        properties.put(mailHost, true);
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        String myAccount = senderEmail;
        String password = senderPassword;

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(msg);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }

    }

}
