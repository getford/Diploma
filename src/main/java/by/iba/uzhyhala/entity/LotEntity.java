package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "lot", schema = "public", catalog = "auction")
public class LotEntity implements Serializable {
    private static final long serialVersionUID = 1;

    private int id;
    private String uuid;
    private String uuidUserSeller;
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
    private Integer idCategory;
    private String status;
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
    @Column(name = "uuid_user_seller", nullable = true, length = -1)
    public String getUuidUserSeller() {
        return uuidUserSeller;
    }

    public void setUuidUserSeller(String uuidUserSeller) {
        this.uuidUserSeller = uuidUserSeller;
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
    @Column(name = "date_add", nullable = true, length = 20)
    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    @Basic
    @Column(name = "date_start", nullable = true, length = 20)
    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "date_end", nullable = true, length = 20)
    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Basic
    @Column(name = "time_start", nullable = true, length = 15)
    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    @Basic
    @Column(name = "time_end", nullable = true, length = 15)
    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
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

    @Basic
    @Column(name = "status", nullable = true, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotEntity lotEntity = (LotEntity) o;
        return id == lotEntity.id &&
                Objects.equals(uuid, lotEntity.uuid) &&
                Objects.equals(uuidUserSeller, lotEntity.uuidUserSeller) &&
                Objects.equals(name, lotEntity.name) &&
                Objects.equals(information, lotEntity.information) &&
                Objects.equals(cost, lotEntity.cost) &&
                Objects.equals(blitzCost, lotEntity.blitzCost) &&
                Objects.equals(stepCost, lotEntity.stepCost) &&
                Objects.equals(dateAdd, lotEntity.dateAdd) &&
                Objects.equals(dateStart, lotEntity.dateStart) &&
                Objects.equals(dateEnd, lotEntity.dateEnd) &&
                Objects.equals(timeStart, lotEntity.timeStart) &&
                Objects.equals(timeEnd, lotEntity.timeEnd) &&
                Objects.equals(uuidUserClient, lotEntity.uuidUserClient) &&
                Objects.equals(idCategory, lotEntity.idCategory) &&
                Objects.equals(status, lotEntity.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uuid, uuidUserSeller, name, information, cost, blitzCost, stepCost, dateAdd, dateStart, dateEnd, timeStart, timeEnd, uuidUserClient, idCategory, status);
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid", nullable = false, insertable = false, updatable = false)
    public BetEntity getBetByUuid() {
        return betByUuid;
    }

    public void setBetByUuid(BetEntity betByUuid) {
        this.betByUuid = betByUuid;
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid", nullable = false, insertable = false, updatable = false)
    public FeedbackEntity getFeedbackByUuid() {
        return feedbackByUuid;
    }

    public void setFeedbackByUuid(FeedbackEntity feedbackByUuid) {
        this.feedbackByUuid = feedbackByUuid;
    }

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id", insertable = false, updatable = false)
    public CategoryEntity getCategoryByIdCategory() {
        return categoryByIdCategory;
    }

    public void setCategoryByIdCategory(CategoryEntity categoryByIdCategory) {
        this.categoryByIdCategory = categoryByIdCategory;
    }
}
