package by.iba.uzhyhala;

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
/*
        Registration doRegistration = new Registration();
       *//* doRegistration.doRegistration("qwe", "qwe", "qwe@qwe.qwe");
        MailUtil sendMail = new MailUtil();*//*

        LotHandler lotHandler = new LotHandler();

        lotHandler.prepareToAddLot("qwe", "10", "123", "10", "1234", "123", "123", "qwe");
        //  lotHandler.addLot();
        lotHandler.showLots();*/

        /*Registration registration = new Registration();
        registration.doRegistration("qwe", "qwe", "qwe@qwe.qwe");*/

        // new MailUtil().testSendMail(VariablesUtil.EMAIL_TEST, "qwe", "qqq");

/*        Profile profile = new Profile("qwe@qwe.qwe");
        profile.getUserPersonalInformation();*/

/*        BetHandler betHandler = new BetHandler();
        betHandler.doBet();*/

//        LotHandler lotHandler = new LotHandler("qwe");
//        lotHandler.addLot(session, "lot_1", "info_lot_1", "123", "11223", "11", "12/12/2010", "01/02/2011", "10:10:10", "20:20:20", 1);


//        CommonUtil.getCategoryById(1);
    }
}
