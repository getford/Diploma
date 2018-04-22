package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    private static final Logger LOGGER = Logger.getLogger(MailUtil.class);
    private URL url = null;
    private static String timeNow = new SimpleDateFormat(VariablesUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime());

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
            message.setFrom(new InternetAddress(VariablesUtil.EMAIL_SUPPORT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(mailBody, "text/html; charset=utf-8");
            Transport.send(message);
            LOGGER.info("Sent message to [ " + email + " ] successfully.");
        } catch (MessagingException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public void sendErrorMailForAdmin(String error) {
        String mailBody = "" +
                "<br/>" + timeNow +
                "<br/>" + error +
                "<br/>";
        setupParametersForMessage(VariablesUtil.EMAIL_SUPPORT, "Error " + timeNow, mailBody);
    }

    public void sendMailRegistration(String email, String login, String password_, HttpServletRequest request) {
        try {
            url = new URL(request.getRequestURL().toString());
            String subject = "Successfully registration";
            String mailBody = "<br/> " + timeNow + "<br/>" +
                    "<p>Hello,</p>" +
                    "<p>You will be successfully registered in Auction</p>" +
                    "<p>" +
                    "<b>Your login: </b>" + login + "" +
                    "<br/><b>Your password: </b>" + password_ + "" +
                    "</p>" +
                    "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/profile.jsp?login=" + login + "\">" +
                    "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/profile.jsp?user=" + login + "</a></p>";

            setupParametersForMessage(VariablesUtil.EMAIL_TEST, subject, mailBody);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    void sendMailChangeLotStatus(String email, String uuidLot, String status, HttpServletRequest request) {
        try {
            url = new URL(request.getRequestURL().toString());
            String subject = "Статус лота был успешно изменен";
            String mailBody = "<br/> " + timeNow + "<br/>" +
                    "<p>Здравствуйте,</p>" +
                    "<p>Уведомляем вас о том, что статус вашего лота, был успешно изменен</p>" +
                    "<p>" +
                    "<b>Новый статус: </b>" + status + "" +
                    "<br/><b>Уникальный идентификатор лота: </b>" + uuidLot + "</p>" +
                    "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuidLot + "\">" +
                    "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuidLot + "</a></p>";
            setupParametersForMessage(VariablesUtil.EMAIL_TEST, subject, mailBody);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}