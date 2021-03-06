package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static by.iba.uzhyhala.util.VariablesUtil.HIBERNATE_CONFIG;

public class HibernateUtil {
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(HIBERNATE_CONFIG).buildSessionFactory();
        } catch (RuntimeException ex) {
            LOGGER.error("Initial SessionFactory creation failed.\t" + ex.getLocalizedMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}