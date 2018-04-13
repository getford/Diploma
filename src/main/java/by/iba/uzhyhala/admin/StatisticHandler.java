package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class StatisticHandler {
    private static final Logger logger = Logger.getLogger(StatisticHandler.class);

    private Session session;

    public String getStatisticAddLotForDay() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            List<Object[]> list = session.createSQLQuery("SELECT date_add, count(date_add) FROM lot " +
                    "GROUP BY date_add ORDER BY date_add ASC").list();
            StringBuilder dataForChart = new StringBuilder();
            for (Object[] record : list) {
                dataForChart.append("[\"").append(record[0]).append("\",").append(record[1]).append("],");
            }
            return dataForChart.substring(0, dataForChart.length() - 1);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            throw new IllegalArgumentException("Cannot get data");
        }
    }
}