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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdUserSeller() {
        return idUserSeller;
    }

    public void setIdUserSeller(int idUserSeller) {
        this.idUserSeller = idUserSeller;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBlitzCost() {
        return blitzCost;
    }

    public void setBlitzCost(String blitzCost) {
        this.blitzCost = blitzCost;
    }

    public String getStepCost() {
        return stepCost;
    }

    public void setStepCost(String stepCost) {
        this.stepCost = stepCost;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getUuidUserClient() {
        return uuidUserClient;
    }

    public void setUuidUserClient(String uuidUserClient) {
        this.uuidUserClient = uuidUserClient;
    }

    @Override
    public String toString() {
        return "LotTO{" +
                "idCategory=" + idCategory +
                ", idUserSeller=" + idUserSeller +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", cost='" + cost + '\'' +
                ", blitzCost='" + blitzCost + '\'' +
                ", stepCost='" + stepCost + '\'' +
                ", dateAdd='" + dateAdd + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", uuidUserClient='" + uuidUserClient + '\'' +
                '}';
    }
}