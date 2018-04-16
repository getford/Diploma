package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AddressEntity;
import by.iba.uzhyhala.entity.PersonalInformationEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class Profile {
    private static final Logger logger = Logger.getLogger(Profile.class);

    private Session session;
    private String type;
    private String uuidUser;

    public Profile(String loginOrEmail) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            logger.debug(getClass().getName() + " constructor");
            this.type = CommonUtil.loginOrEmail(loginOrEmail);
            this.uuidUser = CommonUtil.getUUIDUserByLoginEmail(session, loginOrEmail, type);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<PersonalInformationEntity> getUserPersonalInformation() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            logger.debug(getClass().getName() + " getUserPersonalInformation");
            return session.createQuery("SELECT p FROM " + VariablesUtil.ENTITY_PERSONAL_INFORMATION + " p WHERE uuid_user = :uuid").setParameter("uuid", uuidUser).list();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<AddressEntity> getUserAddress() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            logger.debug(getClass().getName() + " getUserPersonalInformation");
            return session.createQuery("SELECT p FROM " + VariablesUtil.ENTITY_ADDRESS + " p WHERE uuid_user = :uuid").setParameter("uuid", uuidUser).list();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public String getUuidUser() {
        return uuidUser;
    }
}
