package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StatisticHandler {
    private static final Logger LOGGER = Logger.getLogger(StatisticHandler.class);

    public String prepareChartDataFormat(String query) {    // return [date, count]
        LOGGER.debug("prepareChartDataFormat");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            List<Object[]> list = session.createSQLQuery(query).list();
            StringBuilder dataForChart = new StringBuilder();
            for (Object[] record : list) {
                if (!String.valueOf(record[0]).equals("null"))
                    dataForChart.append("[\"").append(changeDataFormat(record[0])).append("\",").append(record[1]).append("],");
            }
            LOGGER.debug(dataForChart.substring(0, dataForChart.length() - 1));
            return dataForChart.substring(0, dataForChart.length() - 1);
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            throw new IllegalArgumentException("Cannot get data");
        }
    }

    private String changeDataFormat(Object oldDateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VariablesUtil.PATTERN_DATE);
            Date d = simpleDateFormat.parse(String.valueOf(oldDateString));
            simpleDateFormat.applyPattern(VariablesUtil.PATTERN_DATE_FOR_CHARTS);
            return simpleDateFormat.format(d);
        } catch (ParseException ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return String.valueOf(oldDateString);
        }
    }

    public String countStatistic(String query) {
        LOGGER.debug("countStatistic");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return String.valueOf(session.createSQLQuery(query).list().get(0));
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            throw new IllegalArgumentException("Cannot get data");
        }
    }
}