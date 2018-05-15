package by.iba.uzhyhala.api.user;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static by.iba.uzhyhala.util.VariablesUtil.ENTITY_AUTH_INFO;


@WebServlet("/getapikey")
public class UserApiKey extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserApiKey.class);
    private static final long serialVersionUID = -8938087671620948934L;

    @SuppressFBWarnings({"XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER", "XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER"})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String responseMessage;
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String uuid = req.getParameter("uuid");
            String key = String.valueOf(UUID.randomUUID()).replaceAll("-", "").toUpperCase();
            if (StringUtils.isBlank(CommonUtil.getUserLoginByUUID(uuid)))
                resp.getWriter().write("{\"exception\":\"user uuid: " + uuid + " isn't correct\"}");
            else {
                if (!CommonUtil.isUserHaveApiKey(uuid)) {
                    session.createQuery("UPDATE " + ENTITY_AUTH_INFO + " SET api_key = :key WHERE uuid = :uuid")
                            .setParameter("key", key)
                            .setParameter("uuid", uuid)
                            .executeUpdate();
                    responseMessage = "{\"api_key\":\"" + key + "\"}";
                } else {
                    responseMessage = "{\"exception\":\"user uuid: " + uuid + " already have api_key\"}";
                }
                resp.getWriter().write(responseMessage);
            }
        } catch (Exception ex) {
            resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            LOGGER.error(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
