package by.iba.uzhyhala.lot.to;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BetTO {

    @SerializedName("uuid_user")
    @Expose
    private String uuidUser;
    @SerializedName("bet")
    @Expose
    private int bet;
    @SerializedName("blitz_cost")
    @Expose
    private int blitzCost;
    @SerializedName("old_cost")
    @Expose
    private int oldCost;
    @SerializedName("new_cost")
    @Expose
    private int newCost;
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

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBlitzCost() {
        return blitzCost;
    }

    public void setBlitzCost(int blitzCost) {
        this.blitzCost = blitzCost;
    }

    public int getOldCost() {
        return oldCost;
    }

    public void setOldCost(int oldCost) {
        this.oldCost = oldCost;
    }

    public int getNewCost() {
        return newCost;
    }

    public void setNewCost(int newCost) {
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
                ", bet=" + bet +
                ", blitzCost='" + blitzCost + '\'' +
                ", oldCost=" + oldCost +
                ", newCost=" + newCost +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}