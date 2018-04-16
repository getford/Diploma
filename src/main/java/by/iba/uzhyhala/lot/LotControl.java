package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class LotControl {
    private static final Logger logger = Logger.getLogger(LotHandler.class);
    private Session session;
    private String uuidLot;

    public LotControl(String uuidLot) {
        try {
            this.uuidLot = uuidLot;
      /*    session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            logger.debug(getClass().getName() + " constructor");
            this.type = CommonUtil.loginOrEmail(loginOrEmail);
            this.idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);*/
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } /*finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
    }

    public String getHistoryBets() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String betHistory = session.createQuery("SELECT b.bulk FROM " + VariablesUtil.ENTITY_BET +
                    " b WHERE b.uuid = :uuid").setParameter("uuid", uuidLot).list().get(0).toString();




            return null;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<LotEntity> getLotInfoByUuid() {
        logger.debug(getClass().getName() + " getLotInfoByUuid");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l WHERE uuid = :uuid", LotEntity.class)
                    .setParameter("uuid", uuidLot).getResultList();
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
