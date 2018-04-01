package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.UUID;

public class LotHandler {
    private static final Logger logger = Logger.getLogger(LotHandler.class);
    private Session session;
    private Gson gson;

    private LotEntity lotTO;

    public LotHandler() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        gson = new Gson();
    }

    public void addLot() {
        System.out.println(lotTO.toString());
        session.save(lotTO);
        session.beginTransaction().commit();
        session.close();

    }

    public void showLots() {
        Query query = session.createSQLQuery("SELECT * FROM lot");
        System.out.println(Arrays.toString(query.list().toArray()));
        int i = 1000000;
    }

    public String prepareToAddLot(String name, String startCost, String blitzCost, String stepCost, String duraion, String dateStart, String dateEnd, String information) {
        lotTO = new LotEntity();
        lotTO.setNameLot(name);
        lotTO.setIdCategory(1);
        lotTO.setStartCost(startCost);
        lotTO.setBlitzCost(blitzCost);
        lotTO.setStepCost(stepCost);
        lotTO.setDuration(duraion);
        lotTO.setWhenStart(dateStart);
        lotTO.setWhenStop(dateEnd);
        lotTO.setInformation(information);
        lotTO.setLotUuid(UUID.randomUUID().toString());

        return gson.toJson(lotTO);
    }
}
