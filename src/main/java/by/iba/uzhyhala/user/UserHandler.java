package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class UserHandler {
    private static final Logger LOGGER = Logger.getLogger(UserHandler.class);
    private Session session;

    public List<AuthInfoEntity> getAllUser() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String query = "SELECT a FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a";
            return session.createQuery(query).list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            throw new IllegalArgumentException("Cannot get data");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
