package by.iba.uzhyhala.api.user;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
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

@WebServlet("/getapikey")
public class UserApiKey extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserApiKey.class);

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
                if (CommonUtil.isUserHaveApiKey(uuid)) {
                    session.createQuery("UPDATE " + VariablesUtil.ENTITY_AUTH_INFO + " SET api_key = :key WHERE uuid = :uuid")
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
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
