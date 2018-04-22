package by.iba.uzhyhala.api.to;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LotFullFieldTOAPI extends LotAddTOAPI {

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("date_add")
    @Expose
    private String dateAdd;

    @SerializedName("date_end")
    @Expose
    private String dateEnd;

    @SerializedName("time_end")
    @Expose
    private String timeEnd;

    @SerializedName("uuid_user_client")
    @Expose
    private String uuidUserClient;

    @SerializedName("status")
    @Expose
    private String status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LotFullFieldTOAPI{" +
                "uuid='" + uuid + '\'' +
                ", dateAdd='" + dateAdd + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", uuidUserClient='" + uuidUserClient + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
