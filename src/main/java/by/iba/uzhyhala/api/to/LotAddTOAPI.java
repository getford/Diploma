package by.iba.uzhyhala.api.to;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LotAddTOAPI {

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

}