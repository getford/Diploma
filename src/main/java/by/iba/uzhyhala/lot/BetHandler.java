package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetTO;
import by.iba.uzhyhala.lot.to.BulkBetTO;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BetHandler {
    private static final Logger logger = Logger.getLogger(BetHandler.class);

    private Gson gson = new Gson();

    private static final String jsonBulk = "{\"uuid_lot\":\"46b50288-66bf-4e37-a71d-a62d55bf3e50\",\"uuid_seller\":" +
            "\"14209d9b-dc3a-4daa-9fe9-6d056febf3af\",\"uuid_client\":\"\",\"status\":\"active\",\"bets\":[{" +
            "\"uuid_user\":\"d1230270-be57-4f82-84dd-a8f4b3286d0f\",\"bet\":10,\"blitz_cost\":\"100\",\"old_cost" +
            "\":10,\"new_cost\":20,\"date\":\"06/04/2018\",\"time\":\"02:30:33\"},{\"uuid_user\":" +
            "\"727378a4-9fb0-466e-85ce-2af4445032d2\",\"bet\":10,\"blitz_cost\":\"100\",\"old_cost\":20,\"new_cost" +
            "\":30,\"date\":\"06/04/2018\",\"time\":\"04:25:45\"}]}";

    public BetHandler() {

    }

    public void doBet() {

    }


    public void parseBetBulk() {
        BulkBetTO bulkBetTO = gson.fromJson(jsonBulk, BulkBetTO.class);

        int bet = 29;
        String uuidUser = "ca20bdaf-0be3-4427-9d58-1667968322b9";


        List<BetTO> betTOList = new ArrayList<>(bulkBetTO.getBets());


        BetTO betTO = new BetTO();
        betTO.setBet(bet);
        betTO.setDate(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new Date().getTime())));
        betTO.setOldCost(bulkBetTO.getBets().get(bulkBetTO.getBets().size() - 1).getNewCost());
        betTO.setNewCost(bulkBetTO.getBets().get(bulkBetTO.getBets().size() - 1).getNewCost() + bet);
        betTO.setBlitzCost(bulkBetTO.getBets().get(0).getBlitzCost());
        betTO.setUuidUser(uuidUser);
        betTO.setTime(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new Date().getTime())));

        betTOList.add(betTO);

        bulkBetTO.setBets(betTOList);

        System.err.println(gson.toJson(bulkBetTO));

        int i = 1000000;
    }
}
