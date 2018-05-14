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
    private static final Logger LOGGER = Logger.getLogger(Profile.class);

    private String uuidUser;

    public Profile(String loginOrEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug(getClass().getName() + " constructor");
            this.uuidUser = CommonUtil.getUUIDUserByLoginEmail(session, loginOrEmail, CommonUtil.loginOrEmail(loginOrEmail));
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }

    public List<PersonalInformationEntity> getUserPersonalInformation() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug(getClass().getName() + " getUserPersonalInformation");
            return session.createQuery("SELECT p FROM " + VariablesUtil.ENTITY_PERSONAL_INFORMATION + " p WHERE uuid_user = :uuid").setParameter("uuid", uuidUser).list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public List<AddressEntity> getUserAddress() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug(getClass().getName() + " getUserPersonalInformation");
            return session.createQuery("SELECT p FROM " + VariablesUtil.ENTITY_ADDRESS + " p WHERE uuid_user = :uuid").setParameter("uuid", uuidUser).list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public String getUuidUser() {
        return uuidUser;
    }
}
