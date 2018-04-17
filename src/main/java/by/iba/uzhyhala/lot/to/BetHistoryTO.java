package by.iba.uzhyhala.lot.to;

public class BetHistoryTO {
    private String userName;
    private Integer bet;
    private String date;
    private String time;

    public BetHistoryTO(String userName, Integer bet, String date, String time) {
        this.userName = userName;
        this.bet = bet;
        this.date = date;
        this.time = time;
    }

    public BetHistoryTO() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
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
        return "BetHistoryTO{" +
                "userName='" + userName + '\'' +
                ", bet=" + bet +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
