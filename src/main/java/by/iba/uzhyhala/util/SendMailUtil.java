package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class SendMailUtil {
    private static final Logger logger = Logger.getLogger(SendMailUtil.class);
    private URL url = null;

    private void setupParametersForMessage(String email, String subject, String mailBody) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", VariablesUtil.EMAIL_HOST);
            props.put("mail.smtp.port", VariablesUtil.EMAIL_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(VariablesUtil.EMAIL_SUPPORT, VariablesUtil.EMAIL_SUPPORT_PASSWORD);
                }
            };
            Session session = Session.getInstance(props, auth);
            System.setProperty("https.protocols", "TLSv1.1");

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(VariablesUtil.EMAIL_SUPPORT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(mailBody, "text/html; charset=utf-8");
            Transport.send(message);
            logger.info("Sent message to [" + email + "] successfully.");

        } catch (MessagingException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public void sendMailRegistration(String email, String login, String password_, HttpServletRequest request) {
        try {
            url = new URL(request.getRequestURL().toString());
            String subject = "Successfully registration";
            String mailBody = "<p>Hello,</p>" +
                    "<p>You will be successfully registered in Auction</p>" +
                    "<p>" +
                    "<b>Your login: </b>" + login + "" +
                    "<br/><b>Your password: </b>" + password_ + "" +
                    "</p>" +
                    "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/profile.jsp?login=" + login + "\">" +
                    "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/profile.jsp?login=" + login + "</a></p>";

            setupParametersForMessage(VariablesUtil.EMAIL_TEST, subject, mailBody);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
    }

    public void testSendMail(String email, String login, String password_) {
        String subject = "Successfully registration";
        String mailBody = "<p>Hello,</p>" +
                "<p>You will be successfully registered in Auction</p>" +
                "<p>" +
                "<b>Your login: </b>" + login + "" +
                "<br/><b>Your password: </b>" + password_ + "" +
                "</p>";

        setupParametersForMessage(VariablesUtil.EMAIL_TEST, subject, mailBody);
    }
}