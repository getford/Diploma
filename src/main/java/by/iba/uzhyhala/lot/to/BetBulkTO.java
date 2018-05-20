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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBlitzCost() {
        return blitzCost;
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

    public String getUuidClient() {
        return uuidClient;
    }

    public void setUuidClient(String uuidClient) {
        this.uuidClient = uuidClient;
    }
}
