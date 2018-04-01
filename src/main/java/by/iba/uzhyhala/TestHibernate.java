package by.iba.uzhyhala;

import by.iba.uzhyhala.lot.LotHandler;
import by.iba.uzhyhala.user.Authorization;
import by.iba.uzhyhala.user.Registration;
import by.iba.uzhyhala.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class TestHibernate {
    private static final Logger logger = Logger.getLogger(TestHibernate.class);

    public static void main(String[] args) {

        logger.debug("+++");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        // Query query = session.createQuery("select a.uuid from AuthInfoEntity a");
        //    Query query = session.createSQLQuery("select * from auth_info");

        // List<?> list = query.list();

        Registration doRegistration = new Registration();
       /* doRegistration.doRegistration("qwe", "qwe", "qwe@qwe.qwe");
        SendMailUtil sendMail = new SendMailUtil();*/

        Authorization doAuthorization = new Authorization();
      //  System.out.println(doAuthorization.doLogin("auction.diploma@gmail.com", "qwe"));

        LotHandler lotHandler = new LotHandler();

        lotHandler.prepareToAddLot("qwe", "10", "123", "10", "1234", "123", "123", "qwe");
      //  lotHandler.addLot();
        lotHandler.showLots();
    }
}
