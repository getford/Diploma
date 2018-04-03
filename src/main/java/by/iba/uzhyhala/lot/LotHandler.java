package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Arrays;

public class LotHandler {
    private static final Logger logger = Logger.getLogger(LotHandler.class);
    private Session session;
    private Gson gson;

    private LotEntity lotEntity;

    public LotHandler() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        gson = new Gson();
    }

    public void addLot() {
        System.out.println(lotEntity.toString());
        session.save(lotEntity);
        session.beginTransaction().commit();
        session.close();

    }

    public void showLots() {
        Query query = session.createSQLQuery("SELECT * FROM lot");
        System.out.println(Arrays.toString(query.list().toArray()));
        int i = 1000000;
    }

    public String prepareToAddLot(String name, String startCost, String blitzCost, String stepCost, String duraion, String dateStart, String dateEnd, String information) {
        /*lotEntity = new LotEntity();
        lotEntity.setNameLot(name);
        lotEntity.setIdCategory(1);
        lotEntity.setStartCost(startCost);
        lotEntity.setBlitzCost(blitzCost);
        lotEntity.setStepCost(stepCost);
        lotEntity.setDuration(duraion);
        lotEntity.setWhenStart(dateStart);
        lotEntity.setWhenStop(dateEnd);
        lotEntity.setInformation(information);
        lotEntity.setLotUuid(UUID.randomUUID().toString());

        return gson.toJson(lotEntity);*/
        return null;
    }
}
