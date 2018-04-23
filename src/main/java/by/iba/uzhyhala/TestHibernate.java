package by.iba.uzhyhala;

import by.iba.uzhyhala.admin.StatisticHandler;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;

public class TestHibernate {
    private static final Logger LOGGER = Logger.getLogger(TestHibernate.class);

    public static void main(String[] args) {
/*

        logger.debug("+++");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
*/

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


/*        StatisticHandler statisticHandler = new StatisticHandler();
        statisticHandler.getStatisticAddLotForDay();*/

/*
        DocumentHandler documentHandler = new DocumentHandler();
        documentHandler.generatePDF();
*/

        //  new LotControl("").getHistoryBets();

/*        String s =UUID.randomUUID().toString();
        System.err.println(s);
        System.err.println(String.valueOf(s).substring(0, 8));*/

        //   System.err.println(CommonUtil.getLotDateEnd("10:02:10.000", VariablesUtil.LOT_TIME_SEC));

/*        LocalDateTime localDateTime = LocalDateTime.parse("2018-04-16T19:20:17.866");
        System.err.println(localDateTime.toLocalTime());
        LocalDateTime l = localDateTime.plusMinutes(20);
        System.err.println(l.toLocalTime());
        LocalDateTime ll = localDateTime.plusSeconds(10);
        System.err.println(ll.toLocalTime());*/

  /*      System.err.println(Thread.currentThread());


        Thread thread = new Thread();
        thread.run();*/

/*        LotControl lotControl = new LotControl("d83a7aa9-a099-46e1-94a9-af145ac54b8e");
        lotControl.returnEndTime();*/

        StatisticHandler statisticHandler = new StatisticHandler();
        System.err.println(statisticHandler.prepareChartDataFormat(VariablesUtil.QUERY_CHART_DATA_END_DATE_LOT));
    }
}
