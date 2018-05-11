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

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public String getCost() {
        return cost;
    }

    public String getBlitzCost() {
        return blitzCost;
    }

    public String getStepCost() {
        return stepCost;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

}