package utils;


import database.tables.Preference;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by shifar on 15/4/16.
 */
public class MailHelper {

    private static String gmailUsername, gmailPassword;

    public static boolean sendMail(String email, String message) {


        System.out.println("Sending email to " + email);

        if (gmailUsername == null || gmailPassword == null) {
            final Preference preference = Preference.getInstance();

            gmailUsername = preference.getString(Preference.KEY_GMAIL_USERNAME);
            gmailPassword = preference.getString(Preference.KEY_GMAIL_PASSWORD);
        }

        System.out.println("u:" + gmailUsername);
        System.out.println("p:" + gmailPassword);


        final Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmailUsername, gmailPassword);
            }
        });

        Message mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(gmailUsername));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mimeMessage.setSubject("New user @ SCD");
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("Mail sent :" + message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        System.out.println("Failed to send mail");

        return false;
    }

}
