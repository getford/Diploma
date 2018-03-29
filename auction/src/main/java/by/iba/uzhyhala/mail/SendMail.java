package by.iba.uzhyhala.mail;

import by.iba.uzhyhala.to.ConfigTO;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SendMail {
    private static final Logger logger = Logger.getLogger(SendMail.class);
    private Map<String, String> paramMailMap = new HashMap<>();
    private URL url = null;

    //TODO: Delete test mail
    private final static String to = "xtudocr4.uz5@20mm.eu";

    public SendMail() {
        prepareMailParam();
    }

    private void prepareMailParam() {
        try {
            String path = getClass().getResource("/config.json").getPath();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            ConfigTO configTO = new Gson().fromJson(bufferedReader, ConfigTO.class);

            paramMailMap.put("host", configTO.getHost());
            paramMailMap.put("port", configTO.getPort());
            paramMailMap.put("from", configTO.getFrom());
            paramMailMap.put("password", configTO.getPassword());
            logger.info("Parameters from json [" + path + "] read successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void paramMessage(String email, String subject, String mailBody) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", paramMailMap.get("host"));
            props.put("mail.smtp.port", paramMailMap.get("port"));
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(paramMailMap.get("from"), paramMailMap.get("password"));
                }
            };
            Session session = Session.getInstance(props, auth);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(paramMailMap.get("from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(mailBody, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("Sent message to [" + email + "] successfully.");
            logger.info("Sent message to [" + email + "] successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMailRegistration(String email, String login, String password_, HttpServletRequest request) {
        try {
            url = new URL(request.getRequestURL().toString());
            String subject = "Successfully registration";
            String mailBody = "<p>Hello,</p>" +
                    "<p>You will be successfully registered in Bug Tracking System</p>" +
                    "<p>" +
                    "<b>Your login: </b>" + login + "" +
                    "<br/><b>Your password: </b>" + password_ + "" +
                    "</p>" +
                    "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/profile.jsp?login=" + login + "\">" +
                    "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/profile.jsp?login=" + login + "</a></p>";

            paramMessage(email, subject, mailBody);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}