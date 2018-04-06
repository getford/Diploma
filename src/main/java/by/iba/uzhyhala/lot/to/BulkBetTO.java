package by.iba.uzhyhala.lot.to;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BulkBetTO {

    @SerializedName("uuid_lot")
    @Expose
    private String uuidLot;

    @SerializedName("uuid_seller")
    @Expose
    private String uuidSeller;

    @SerializedName("uuid_client")
    @Expose
    private String uuidClient;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("bets")
    @Expose
    private List<BetTO> bets = null;

    public String getUuidLot() {
        return uuidLot;
    }

    public void setUuidLot(String uuidLot) {
        this.uuidLot = uuidLot;
    }

    public BulkBetTO withUuidLot(String uuidLot) {
        this.uuidLot = uuidLot;
        return this;
    }

    public String getUuidSeller() {
        return uuidSeller;
    }

    public void setUuidSeller(String uuidSeller) {
        this.uuidSeller = uuidSeller;
    }

    public BulkBetTO withUuidSeller(String uuidSeller) {
        this.uuidSeller = uuidSeller;
        return this;
    }

    public String getUuidClient() {
        return uuidClient;
    }

    public void setUuidClient(String uuidClient) {
        this.uuidClient = uuidClient;
    }

    public BulkBetTO withUuidClient(String uuidClient) {
        this.uuidClient = uuidClient;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BulkBetTO withStatus(String status) {
        this.status = status;
        return this;
    }

    public List<BetTO> getBets() {
        return bets;
    }

    public void setBets(List<BetTO> bets) {
        this.bets = bets;
    }

    public BulkBetTO withBets(List<BetTO> bets) {
        this.bets = bets;
        return this;
    }

    @Override
    public String toString() {
        return "BulkBetTO{" +
                "uuidLot='" + uuidLot + '\'' +
                ", uuidSeller='" + uuidSeller + '\'' +
                ", uuidClient='" + uuidClient + '\'' +
                ", status='" + status + '\'' +
                ", bets=" + bets +
                '}';
    }
}
