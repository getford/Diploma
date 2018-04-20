package by.iba.uzhyhala.api.to;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LotTOAPI {

    @SerializedName("uuid_user_seller")
    @Expose
    private String uuidUserSeller;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("blitz_cost")
    @Expose
    private String blitzCost;
    @SerializedName("step_cost")
    @Expose
    private String stepCost;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("id_category")
    @Expose
    private Integer idCategory;

    public String getUuidUserSeller() {
        return uuidUserSeller;
    }

    public void setUuidUserSeller(String uuidUserSeller) {
        this.uuidUserSeller = uuidUserSeller;
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "LotTOAPI{" +
                "uuidUserSeller='" + uuidUserSeller + '\'' +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", cost='" + cost + '\'' +
                ", blitzCost='" + blitzCost + '\'' +
                ", stepCost='" + stepCost + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", idCategory=" + idCategory +
                '}';
    }
}