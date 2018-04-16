package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetTO;
import by.iba.uzhyhala.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LotControl {
    private static final Logger LOGGER = Logger.getLogger(LotHandler.class);
    private Session session;
    private String uuidLot;

    public LotControl(String uuidLot) {
        try {
            this.uuidLot = uuidLot;
      /*    session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            LOGGER.debug(getClass().getName() + " constructor");
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


    public List<LotEntity> getLotInfoByUuid() {
        LOGGER.debug(getClass().getName() + " getLotInfoByUuid");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l WHERE uuid = :uuid", LotEntity.class)
                    .setParameter("uuid", uuidLot).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<BetHistoryTO> getHistoryBets() {
        LOGGER.info(getClass().getName() + "getHistoryBets method");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            BetBulkTO betBulkTO = new Gson().fromJson(CommonUtil.getJsonBetBulk(session, uuidLot), BetBulkTO.class);
            List<BetTO> betTOList = new ArrayList<>(betBulkTO.getBets());

            List<BetHistoryTO> betHistoryTO = new ArrayList<>();
            for (BetTO bet : betTOList) {
                BetHistoryTO to = new BetHistoryTO();
                to.setUserName(CommonUtil.getUserFirstLastNameByUUID(session, bet.getUuidUser()));
                to.setBet(bet.getBet());
                to.setDate(bet.getDate());
                to.setTime(bet.getTime());

                betHistoryTO.add(to);
            }
            return betHistoryTO;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
