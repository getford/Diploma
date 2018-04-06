package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.PersonalInformationEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class Profile {
    private static final Logger logger = Logger.getLogger(Profile.class);

    private Session session;
    private String type;
    private int idUser;
    private String uuidUser;

    public Profile(String loginOrEmail) {
        logger.debug(getClass().getName() + " constructor");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        this.type = CommonUtil.loginOrEmail(loginOrEmail);
        this.idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);
        this.uuidUser = CommonUtil.getUUIDUserByLoginEmail(session, loginOrEmail, type);
    }

    public List<PersonalInformationEntity> getUserPersonalInformation() {
        logger.debug(getClass().getName() + " getUserPersonalInformation");
        String selectQuery = "SELECT * FROM personal_information WHERE id_user = " + idUser + "";
        return session.createSQLQuery(selectQuery).getResultList();
    }

    public String getUserNameByUuid(String uuid){
        return null;
    }

    public String getUuidUser() {
        return uuidUser;
    }
}
