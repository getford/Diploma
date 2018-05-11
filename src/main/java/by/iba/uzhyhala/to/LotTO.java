package by.iba.uzhyhala.to;

public class LotTO {
    private int idCategory;
    private int idUserSeller;
    private String uuid;
    private String name;
    private String information;
    private String cost;
    private String blitzCost;
    private String stepCost;
    private String dateAdd;
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
    private String uuidUserClient;

    public LotTO(int idCategory, int idUserSeller, String uuid, String name, String information, String cost, String blitzCost, String stepCost, String dateAdd, String dateStart, String dateEnd, String timeStart, String timeEnd, String uuidUserClient) {
        this.idCategory = idCategory;
        this.idUserSeller = idUserSeller;
        this.uuid = uuid;
        this.name = name;
        this.information = information;
        this.cost = cost;
        this.blitzCost = blitzCost;
        this.stepCost = stepCost;
        this.dateAdd = dateAdd;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.uuidUserClient = uuidUserClient;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }
}