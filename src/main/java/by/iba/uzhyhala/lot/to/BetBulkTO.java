package by.iba.uzhyhala.lot.to;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BetBulkTO {

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
    @SerializedName("blitz_cost")
    @Expose
    private Integer blitzCost;
    @SerializedName("step")
    @Expose
    private Integer step;
    @SerializedName("bets")
    @Expose
    private List<BetTO> bets = null;

    public String getUuidLot() {
        return uuidLot;
    }

    public void setUuidLot(String uuidLot) {
        this.uuidLot = uuidLot;
    }

    public String getUuidSeller() {
        return uuidSeller;
    }

    public void setUuidSeller(String uuidSeller) {
        this.uuidSeller = uuidSeller;
    }

    public String getUuidClient() {
        return uuidClient;
    }

    public void setUuidClient(String uuidClient) {
        this.uuidClient = uuidClient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBlitzCost() {
        return blitzCost;
    }

    public void setBlitzCost(Integer blitzCost) {
        this.blitzCost = blitzCost;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public List<BetTO> getBets() {
        return bets;
    }

    public void setBets(List<BetTO> bets) {
        this.bets = bets;
    }

    @Override
    public String toString() {
        return "BetBulkTO{" +
                "uuidLot='" + uuidLot + '\'' +
                ", uuidSeller='" + uuidSeller + '\'' +
                ", uuidClient='" + uuidClient + '\'' +
                ", status='" + status + '\'' +
                ", blitzCost=" + blitzCost +
                ", step=" + step +
                ", bets=" + bets +
                '}';
    }
}
