package by.iba.uzhyhala;

import by.iba.uzhyhala.mail.SendMail;
import by.iba.uzhyhala.user.DoRegistration;
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

        DoRegistration doRegistration = new DoRegistration();
        doRegistration.doRegistration("qwe", "qwe");
        SendMail sendMail = new SendMail();
    }
}
