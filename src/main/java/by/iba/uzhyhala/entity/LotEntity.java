package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "lot", schema = "public", catalog = "auction")
public class LotEntity {
    private int id;
    private String uuid;
    private Integer idUserSeller;
    private String name;
    private String information;
    private String cost;
    private String blitzCost;
    private String stepCost;
    private Date dateAdd;
    private Date dateStart;
    private Date dateEnd;
    private Time timeStart;
    private Time timeEnd;
    private String uuidUserClient;
    private Integer idCategory;
    private BetEntity betByUuid;
    private FeedbackEntity feedbackByUuid;
    private CategoryEntity categoryByIdCategory;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid", nullable = false, length = -1)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "id_user_seller", nullable = true, insertable = false, updatable = false)
    public Integer getIdUserSeller() {
        return idUserSeller;
    }

    public void setIdUserSeller(Integer idUserSeller) {
        this.idUserSeller = idUserSeller;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "information", nullable = true, length = -1)
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Basic
    @Column(name = "cost", nullable = true, length = -1)
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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
    @Column(name = "date_add", nullable = true)
    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    @Basic
    @Column(name = "date_start", nullable = true)
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "date_end", nullable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Basic
    @Column(name = "time_start", nullable = true)
    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    @Basic
    @Column(name = "time_end", nullable = true)
    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Basic
    @Column(name = "uuid_user_client", nullable = true, length = -1)
    public String getUuidUserClient() {
        return uuidUserClient;
    }

    public void setUuidUserClient(String uuidUserClient) {
        this.uuidUserClient = uuidUserClient;
    }

    @Basic
    @Column(name = "id_category", nullable = true)
    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotEntity lotEntity = (LotEntity) o;

        if (id != lotEntity.id) return false;
        if (uuid != null ? !uuid.equals(lotEntity.uuid) : lotEntity.uuid != null) return false;
        if (idUserSeller != null ? !idUserSeller.equals(lotEntity.idUserSeller) : lotEntity.idUserSeller != null)
            return false;
        if (name != null ? !name.equals(lotEntity.name) : lotEntity.name != null) return false;
        if (information != null ? !information.equals(lotEntity.information) : lotEntity.information != null)
            return false;
        if (cost != null ? !cost.equals(lotEntity.cost) : lotEntity.cost != null) return false;
        if (blitzCost != null ? !blitzCost.equals(lotEntity.blitzCost) : lotEntity.blitzCost != null) return false;
        if (stepCost != null ? !stepCost.equals(lotEntity.stepCost) : lotEntity.stepCost != null) return false;
        if (dateAdd != null ? !dateAdd.equals(lotEntity.dateAdd) : lotEntity.dateAdd != null) return false;
        if (dateStart != null ? !dateStart.equals(lotEntity.dateStart) : lotEntity.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(lotEntity.dateEnd) : lotEntity.dateEnd != null) return false;
        if (timeStart != null ? !timeStart.equals(lotEntity.timeStart) : lotEntity.timeStart != null) return false;
        if (timeEnd != null ? !timeEnd.equals(lotEntity.timeEnd) : lotEntity.timeEnd != null) return false;
        if (uuidUserClient != null ? !uuidUserClient.equals(lotEntity.uuidUserClient) : lotEntity.uuidUserClient != null)
            return false;
        if (idCategory != null ? !idCategory.equals(lotEntity.idCategory) : lotEntity.idCategory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (idUserSeller != null ? idUserSeller.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (blitzCost != null ? blitzCost.hashCode() : 0);
        result = 31 * result + (stepCost != null ? stepCost.hashCode() : 0);
        result = 31 * result + (dateAdd != null ? dateAdd.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeEnd != null ? timeEnd.hashCode() : 0);
        result = 31 * result + (uuidUserClient != null ? uuidUserClient.hashCode() : 0);
        result = 31 * result + (idCategory != null ? idCategory.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid", nullable = false)
    public BetEntity getBetByUuid() {
        return betByUuid;
    }

    public void setBetByUuid(BetEntity betByUuid) {
        this.betByUuid = betByUuid;
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid", nullable = false)
    public FeedbackEntity getFeedbackByUuid() {
        return feedbackByUuid;
    }

    public void setFeedbackByUuid(FeedbackEntity feedbackByUuid) {
        this.feedbackByUuid = feedbackByUuid;
    }

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    public CategoryEntity getCategoryByIdCategory() {
        return categoryByIdCategory;
    }

    public void setCategoryByIdCategory(CategoryEntity categoryByIdCategory) {
        this.categoryByIdCategory = categoryByIdCategory;
    }
}
