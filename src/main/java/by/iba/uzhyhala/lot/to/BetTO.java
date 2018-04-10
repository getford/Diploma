package by.iba.uzhyhala.lot.to;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BetTO {

    @SerializedName("uuid_user")
    @Expose
    private String uuidUser;
    @SerializedName("uuid_bet")
    @Expose
    private String uuidBet;
    @SerializedName("bet")
    @Expose
    private Integer bet;
    @SerializedName("old_cost")
    @Expose
    private Integer oldCost;
    @SerializedName("new_cost")
    @Expose
    private Integer newCost;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }

    public String getUuidBet() {
        return uuidBet;
    }

    public void setUuidBet(String uuidBet) {
        this.uuidBet = uuidBet;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public Integer getOldCost() {
        return oldCost;
    }

    public void setOldCost(Integer oldCost) {
        this.oldCost = oldCost;
    }

    public Integer getNewCost() {
        return newCost;
    }

    public void setNewCost(Integer newCost) {
        this.newCost = newCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BetTO{" +
                "uuidUser='" + uuidUser + '\'' +
                ", uuidBet='" + uuidBet + '\'' +
                ", bet=" + bet +
                ", oldCost=" + oldCost +
                ", newCost=" + newCost +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}