package kz.monetka.server.services;

import kz.monetka.server.entities.login.User;
import org.apache.log4j.Logger;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Andrey Smirnov
 * @date 13.02.2018
 */
@Service
@Transactional
class EmailService {
    private static final Logger LOGGER = Logger.getLogger(EmailService.class);

    static boolean sendEmail(String toEmail, String code) {
        Ini ini = new Ini();
        try {
            ini.load(EmailService.class.getResourceAsStream("/config.ini"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        Profile.Section section = ini.get("mail");
        String from = section.get("from");
        String host = section.get("host");
        String port = section.get("port");
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.imap.auth.plain.disable", "true");
        properties.setProperty("mail.debug", "false");
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            // Set Subject: header field
            message.setSubject("Напоминание пароля monetka.kz");
            // Now set the actual message
            message.setContent("Ваш пароль : " + code, "text/html; charset=utf-8");
            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            LOGGER.error(mex.getMessage());
        }
        return true;
    }

    private EmailService() {
    }

    public void sendReminder(User dbUser) {
        if (!dbUser.getLogin().isEmpty() && !dbUser.getPassword().isEmpty()) {
            sendEmail(dbUser.getLogin(), dbUser.getPassword());
        }
    }
}
