package by.iba.uzhyhala.to;

public class LotTO {
    private int idCategory;
    private int idUserSeller;
    private int idUserClient;
    private String nameLot;
    private String startCost;
    private String blitzCost;
    private String stepCost;
    private String duration;
    private String dateStart;
    private String dateEnd;
    private String information;
    private String lotUuid;

    public LotTO(int idCategory, int idUserSeller, int idUserClient, String nameLot, String startCost, String blitzCost, String stepCost, String duration, String dateStart, String dateEnd, String information, String lotUuid) {
        this.idCategory = idCategory;
        this.idUserSeller = idUserSeller;
        this.idUserClient = idUserClient;
        this.nameLot = nameLot;
        this.startCost = startCost;
        this.blitzCost = blitzCost;
        this.stepCost = stepCost;
        this.duration = duration;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.information = information;
        this.lotUuid = lotUuid;
    }

    public LotTO() {

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

    public int getIdUserClient() {
        return idUserClient;
    }

    public void setIdUserClient(int idUserClient) {
        this.idUserClient = idUserClient;
    }

    public String getNameLot() {
        return nameLot;
    }

    public void setNameLot(String nameLot) {
        this.nameLot = nameLot;
    }

    public String getStartCost() {
        return startCost;
    }

    public void setStartCost(String startCost) {
        this.startCost = startCost;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLotUuid() {
        return lotUuid;
    }

    public void setLotUuid(String lotUuid) {
        this.lotUuid = lotUuid;
    }

    @Override
    public String toString() {
        return "LotTO{" +
                "idCategory=" + idCategory +
                ", idUserSeller=" + idUserSeller +
                ", idUserClient=" + idUserClient +
                ", nameLot='" + nameLot + '\'' +
                ", startCost='" + startCost + '\'' +
                ", blitzCost='" + blitzCost + '\'' +
                ", stepCost='" + stepCost + '\'' +
                ", duration='" + duration + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", information='" + information + '\'' +
                ", lotUuid='" + lotUuid + '\'' +
                '}';
    }
}