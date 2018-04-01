package by.iba.uzhyhala.entity;

import javax.persistence.*;

@Entity
@Table(name = "lot", schema = "public", catalog = "auction")
public class LotEntity {
    private int id;
    private int idUserSeller;
    private int idCategory;
    private Integer idUserClient;
    private String nameLot;
    private String startCost;
    private String blitzCost;
    private String stepCost;
    private String duration;
    private String whenStart;
    private String information;
    private String lotUuid;
    private String whenStop;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "id_category", nullable = false)
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Basic
    @Column(name = "id_user_seller", nullable = false)
    public int getIdUserSeller() {
        return idUserSeller;
    }

    public void setIdUserSeller(int idUserSeller) {
        this.idUserSeller = idUserSeller;
    }

    @Basic
    @Column(name = "id_user_client", nullable = true)
    public Integer getIdUserClient() {
        return idUserClient;
    }

    public void setIdUserClient(Integer idUserClient) {
        this.idUserClient = idUserClient;
    }

    @Basic
    @Column(name = "name_lot", nullable = true, length = -1)
    public String getNameLot() {
        return nameLot;
    }

    public void setNameLot(String nameLot) {
        this.nameLot = nameLot;
    }

    @Basic
    @Column(name = "start_cost", nullable = true, length = -1)
    public String getStartCost() {
        return startCost;
    }

    public void setStartCost(String startCost) {
        this.startCost = startCost;
    }

    @Basic
    @Column(name = "blitz_cost", nullable = true, length = -1)
    public String getBlitzCost() {
        return blitzCost;
    }

    public void setBlitzCost(String blitzCost) {
        this.blitzCost = blitzCost;
    }

    @Basic
    @Column(name = "step_cost", nullable = true, length = -1)
    public String getStepCost() {
        return stepCost;
    }

    public void setStepCost(String stepCost) {
        this.stepCost = stepCost;
    }

    @Basic
    @Column(name = "duration", nullable = true, length = -1)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "when_start", nullable = true, length = -1)
    public String getWhenStart() {
        return whenStart;
    }

    public void setWhenStart(String whenStart) {
        this.whenStart = whenStart;
    }

    @Basic
    @Column(name = "information", nullable = false, length = -1)
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Basic
    @Column(name = "lot_uuid", nullable = false, length = -1)
    public String getLotUuid() {
        return lotUuid;
    }

    public void setLotUuid(String lotUuid) {
        this.lotUuid = lotUuid;
    }

    @Basic
    @Column(name = "when_stop", nullable = true, length = -1)
    public String getWhenStop() {
        return whenStop;
    }

    public void setWhenStop(String whenStop) {
        this.whenStop = whenStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotEntity lotEntity = (LotEntity) o;

        if (id != lotEntity.id) return false;
        if (idUserSeller != lotEntity.idUserSeller) return false;
        if (idUserClient != null ? !idUserClient.equals(lotEntity.idUserClient) : lotEntity.idUserClient != null)
            return false;
        if (nameLot != null ? !nameLot.equals(lotEntity.nameLot) : lotEntity.nameLot != null) return false;
        if (startCost != null ? !startCost.equals(lotEntity.startCost) : lotEntity.startCost != null) return false;
        if (blitzCost != null ? !blitzCost.equals(lotEntity.blitzCost) : lotEntity.blitzCost != null) return false;
        if (stepCost != null ? !stepCost.equals(lotEntity.stepCost) : lotEntity.stepCost != null) return false;
        if (duration != null ? !duration.equals(lotEntity.duration) : lotEntity.duration != null) return false;
        if (whenStart != null ? !whenStart.equals(lotEntity.whenStart) : lotEntity.whenStart != null) return false;
        if (information != null ? !information.equals(lotEntity.information) : lotEntity.information != null)
            return false;
        if (lotUuid != null ? !lotUuid.equals(lotEntity.lotUuid) : lotEntity.lotUuid != null) return false;
        if (whenStop != null ? !whenStop.equals(lotEntity.whenStop) : lotEntity.whenStop != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUserSeller;
        result = 31 * result + (idUserClient != null ? idUserClient.hashCode() : 0);
        result = 31 * result + (nameLot != null ? nameLot.hashCode() : 0);
        result = 31 * result + (startCost != null ? startCost.hashCode() : 0);
        result = 31 * result + (blitzCost != null ? blitzCost.hashCode() : 0);
        result = 31 * result + (stepCost != null ? stepCost.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (whenStart != null ? whenStart.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        result = 31 * result + (lotUuid != null ? lotUuid.hashCode() : 0);
        result = 31 * result + (whenStop != null ? whenStop.hashCode() : 0);
        return result;
    }
}
