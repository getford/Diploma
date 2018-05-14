package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MailUtil {
    private static final Logger LOGGER = Logger.getLogger(MailUtil.class);
    private static String timeNow
            = new SimpleDateFormat(VariablesUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime());

    private Map<String, File> attachments = new HashMap<>();

    private void setupParametersForMessage(String email, String subject, String mailBody) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", VariablesUtil.EMAIL_HOST);
            props.put("mail.smtp.port", VariablesUtil.EMAIL_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(VariablesUtil.EMAIL_SUPPORT, VariablesUtil.EMAIL_SUPPORT_PASSCODE);
                }
            };
            Session session = Session.getInstance(props, auth);
            System.setProperty("https.protocols", "TLSv1.1");

            Message message = new MimeMessage(session);

            if (!attachments.isEmpty()) {
                Multipart multipart = new MimeMultipart();

                for (Map.Entry<String, File> entry : attachments.entrySet()) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    attachmentBodyPart.attachFile(new File(String.valueOf(entry.getValue())));
                    multipart.addBodyPart(attachmentBodyPart);
                }

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(mailBody, VariablesUtil.EMAIL_CONTENT_TYPE_HTML);
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);
            } else {
                message.setContent(mailBody, VariablesUtil.EMAIL_CONTENT_TYPE_HTML);
            }

            message.setFrom(new InternetAddress(VariablesUtil.EMAIL_SUPPORT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(VariablesUtil.EMAIL_TITLE_PART + subject);
            Transport.send(message);
            LOGGER.info("Sent message to [ " + email + " ] successfully.");
        } catch (MessagingException | IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public void sendMail(String to, String body, String subject) {
        setupParametersForMessage(VariablesUtil.EMAIL_SUPPORT, subject, body);
    }

    public void sendErrorMail(String error) {
        String mailBody = "" +
                "<br/>" + timeNow +
                "<br/><br/>" + error +
                "<br/>";
        setupParametersForMessage(VariablesUtil.EMAIL_SUPPORT, "Error " + timeNow, mailBody);
    }

    public void addAttachment(File file) {
        this.attachments.put(UUID.randomUUID().toString(), file);
    }
}