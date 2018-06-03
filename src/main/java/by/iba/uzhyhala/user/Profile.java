package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AddressEntity;
import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.entity.PersonalInformationEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.iba.uzhyhala.util.CommonUtil.getUUIDUserByLoginEmail;
import static by.iba.uzhyhala.util.CommonUtil.loginOrEmail;
import static by.iba.uzhyhala.util.VariablesUtil.*;

public class Profile {
    private static final Logger LOGGER = Logger.getLogger(Profile.class);

    Profile() {
    }

    public static List<PersonalInformationEntity> getUserPersonalInformation(String loginOrEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug(" getUserPersonalInformation mathod");
            return session
                    .createQuery("SELECT p FROM " + ENTITY_PERSONAL_INFORMATION + " p WHERE uuid_user = :uuid")
                    .setParameter("uuid", getUUIDUserByLoginEmail(loginOrEmail, loginOrEmail(loginOrEmail))).list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public static List<AddressEntity> getUserAddress(String loginOrEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug("getUserAddress method");
            return session
                    .createQuery("SELECT p FROM " + ENTITY_ADDRESS + " p WHERE uuid_user = :uuid")
                    .setParameter("uuid", getUUIDUserByLoginEmail(loginOrEmail, loginOrEmail(loginOrEmail))).list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public static List<AuthInfoEntity> getUserAuthInfo(String loginOrEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug("getUserAuthInfo method");
            return session
                    .createQuery("SELECT p FROM " + ENTITY_AUTH_INFO + " p WHERE uuid = :uuid")
                    .setParameter("uuid", loginOrEmail).list();
           /* return session
                    .createQuery("SELECT p FROM " + ENTITY_AUTH_INFO + " p WHERE uuid_user = :uuid")
                    .setParameter("uuid", getUUIDUserByLoginEmail(loginOrEmail, loginOrEmail(loginOrEmail))).list();*/
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}
