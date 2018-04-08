package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class LotHandler implements Serializable {
    private static final Logger logger = Logger.getLogger(LotHandler.class);

    private Session session;
    private String type;
    private int idUser;

    public LotHandler(String loginOrEmail) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            logger.debug(getClass().getName() + " constructor");
            this.type = CommonUtil.loginOrEmail(loginOrEmail);
            this.idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void addLot() {
        logger.debug(getClass().getName() + " addLot");
    }

    private String prepareBetBulk(String uuidUser) {

        return "";
    }

    public void deleteLot(String uuid) {
        logger.debug(getClass().getName() + " deleteLot");

    }

    public List<LotEntity> getAllLots() {
        logger.debug(getClass().getName() + " showLots");
        return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l").list();
    }

    public List<LotEntity> getUserLot() {
        logger.debug(getClass().getName() + " getUserLot");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l WHERE id_user_seller = :id", LotEntity.class)
                    .setParameter("id", idUser).getResultList();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
