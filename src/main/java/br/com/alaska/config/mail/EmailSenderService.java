package br.com.alaska.config.mail;

import br.com.alaska.exceptions.email.FailedEmailSenderException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class EmailSenderService implements EmailSender {

    @Value("${mailtrap.username}")
    String mailtrapUsername;

    @Value("${mailtrap.password}")
    String mailtrapPassword;

    @Value("${mailtrap.host}")
    String mailtrapHost;

    @Value("${mailtrap.port}")
    String mailtrapPort;

    @Value("${mailtrap.sender}")
    String mailtrapSender;

    @Override
    @Async
    public void send(String to, String email) {
        log.info("Sending e-mail to {}, email {}", to, email);

        String from = mailtrapSender;
        String username = mailtrapUsername;
        String password = mailtrapPassword;
        String host = mailtrapHost;
        String port = mailtrapPort;

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");

        // Get the Session object.
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject("Alaska e-mail confirmation");
            mimeMessage.setHeader("Content-Type", "text/html");
            mimeMessage.setContent(email, "text/html");
            Transport.send(mimeMessage);

            log.info("Sent email successfully....");

        } catch (MessagingException e) {
            log.error("Failed to send e-mail {}", e.getMessage());
            throw new FailedEmailSenderException();
        }
    }
}
