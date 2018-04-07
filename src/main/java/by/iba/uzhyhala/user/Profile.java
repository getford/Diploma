package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.PersonalInformationEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private static final Logger logger = Logger.getLogger(Profile.class);

    private Session session;
    private String type;
    private int idUser;
    private String uuidUser;

    private List<PersonalInformationEntity> personalInformationList = new ArrayList<>();

    public Profile(String loginOrEmail) {
        logger.debug(getClass().getName() + " constructor");
        this.type = CommonUtil.loginOrEmail(loginOrEmail);
        this.idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);
        this.uuidUser = CommonUtil.getUUIDUserByLoginEmail(session, loginOrEmail, type);
    }

    public void getUserPersonalInformation() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            logger.debug(getClass().getName() + " getUserPersonalInformation");
            String selectQuery = "SELECT * FROM personal_information WHERE id_user = " + idUser + "";
            personalInformationList.add((PersonalInformationEntity) session.createSQLQuery(selectQuery).getResultList());
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public String getUserNameByUuid(String uuid) {
        return null;
    }

    public String getUuidUser() {
        return uuidUser;
    }

    public List<PersonalInformationEntity> getPersonalInformationList() {
        return personalInformationList;
    }

    public void setPersonalInformationList(List<PersonalInformationEntity> personalInformationList) {
        this.personalInformationList = personalInformationList;
    }
}
