package by.iba.uzhyhala;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class TestHibernate {
    private static final Logger logger = Logger.getLogger(TestHibernate.class);

    public static void main(String[] args) {

        logger.debug("+++");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Query query = session.createQuery("select a.uuid from AuthInfoEntity a");

        List<?> list = query.list();

        System.out.println(list);
    }
}
