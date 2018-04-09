package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetTO;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BetHandler {
    private static final Logger logger = Logger.getLogger(BetHandler.class);

    private String errorMessage;
    private Gson gson = new Gson();

    private static String jsonBulk = "{\"uuid_lot\":\"46b50288-66bf-4e37-a71d-a62d55bf3e50\",\"uuid_seller\":\"14209d9b-dc3a-4daa-9fe9-6d056febf3af\",\"uuid_client\":\"ca20bdaf-0be3-4427-9d58-1667968322b9\",\"status\":\"active\",\"bets\":[]}";

    public BetHandler() {

    }

    public void doBet() {
        BetBulkTO betBulkTO = gson.fromJson(jsonBulk, BetBulkTO.class);

        int bet = 100;
        String uuidUser = "ca20bdaf-0be3-4427-9d58-1667968322b9";
        int sizeBetArray = betBulkTO.getBets().size() - 1;

        List<BetTO> betTOList = new ArrayList<>(betBulkTO.getBets());


        BetTO betTO = new BetTO();
        if (betBulkTO.getStatus().equals(VariablesUtil.STATUS_LOT_ACTIVE)) {
            if (!(betBulkTO.getBets().get(sizeBetArray).getBlitzCost() >= bet)) {
                if (sizeBetArray == -1) {
                    betTO.setUuidBet(UUID.randomUUID().toString());
                    betTO.setBet(bet);
                    betTO.setDate(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime())));
                    betTO.setOldCost(0);
                    betTO.setNewCost(bet);
                    betTO.setBlitzCost(100);
                    betTO.setUuidUser(uuidUser);
                    betTO.setTime(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date().getTime())));
                    betTOList.add(betTO);
                    betBulkTO.setBets(betTOList);
                } else {
                    betTO.setUuidBet(UUID.randomUUID().toString());
                    betTO.setBet(bet);
                    betTO.setDate(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime())));
                    betTO.setOldCost(betBulkTO.getBets().get(sizeBetArray).getNewCost());
                    betTO.setNewCost(betBulkTO.getBets().get(sizeBetArray).getNewCost() + bet);
                    betTO.setBlitzCost(betBulkTO.getBets().get(0).getBlitzCost());
                    betTO.setUuidUser(uuidUser);
                    betTO.setTime(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date().getTime())));

                    betTOList.add(betTO);
                    betBulkTO.setBets(betTOList);
                }
            } else {
                betBulkTO.setUuidClient(uuidUser);
                betBulkTO.setStatus(VariablesUtil.STATUS_LOT_SALES);

                betTO.setUuidBet(UUID.randomUUID().toString());
                betTO.setBet(bet);
                betTO.setDate(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime())));
                betTO.setOldCost(betBulkTO.getBets().get(sizeBetArray).getNewCost());
                betTO.setNewCost(betBulkTO.getBets().get(sizeBetArray).getNewCost() + bet);
                betTO.setBlitzCost(betBulkTO.getBets().get(0).getBlitzCost());
                betTO.setUuidUser(uuidUser);
                betTO.setTime(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date().getTime())));

                betTOList.add(betTO);
                betBulkTO.setBets(betTOList);
            }
            jsonBulk = gson.toJson(betBulkTO);
            System.err.println(gson.toJson(betBulkTO));
        }
        else{
            errorMessage = "Лот не доступен для ставок";
        }
    }
}
