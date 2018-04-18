package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class StatisticHandler {
    private static final Logger LOGGER = Logger.getLogger(StatisticHandler.class);

    private Session session;

    public String prepareChartDataFormat(String query) {
        LOGGER.debug(getClass().getName() + "\t" + " prepareChartDataFormat");
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            List<Object[]> list = session.createSQLQuery(query).list();
            StringBuilder dataForChart = new StringBuilder();
            for (Object[] record : list) {
                dataForChart.append("[\"").append(record[0]).append("\",").append(record[1]).append("],");
            }
            LOGGER.debug(getClass().getName() + "\t" + dataForChart.substring(0, dataForChart.length() - 1));
            return dataForChart.substring(0, dataForChart.length() - 1);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            throw new IllegalArgumentException("Cannot get data");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}