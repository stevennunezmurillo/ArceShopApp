package com.example.proyectomviles;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email
{
    public static void testMail(String receiverEmail, String subject, String text)
    {
        String from = "arcesshoppingcr@gmail.com";
        final String username = "arcesshoppingcr@gmail.com";//Usuario generado
        final String password = "bemelrtroeaurjez";//Contraseña generada
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);

        try {
            // Create a objeto MimeMessage
            Message message = new MimeMessage(session);
            // Hace set del campo de header
            message.setFrom(new InternetAddress(from));
            // Hace set de la persona
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            message.setSubject(subject);
            message.setText(text);

            // Envio de mensaje
            Transport t = session.getTransport("smtp");
            t.connect(username,password);
            t.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
