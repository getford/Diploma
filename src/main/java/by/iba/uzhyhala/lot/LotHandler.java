package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class LotHandler {
    private static final Logger logger = Logger.getLogger(LotHandler.class);

    private Session session;
    private String type;
    private int idUser;

    public LotHandler(String loginOrEmail) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        type = CommonUtil.loginOrEmail(loginOrEmail);
        idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);
    }

    public LotHandler() {

    }

    public void addLot() {
    }

    public List<LotEntity> showLots() {
        logger.debug(getClass().getName() + " showLots");
        return session.createSQLQuery("SELECT * FROM lot").getResultList();
    }

    public List<LotEntity> getUserLot() {
        logger.debug(getClass().getName() + " getUserLot");
        String selectQuery = "SELECT * FROM lot where id_user_seller = " + idUser + "";
        return session.createSQLQuery(selectQuery).getResultList();
    }
}
