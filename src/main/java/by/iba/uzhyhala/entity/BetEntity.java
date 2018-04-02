package by.iba.uzhyhala.entity;

import javax.persistence.*;

@Entity
@Table(name = "bet", schema = "public", catalog = "auction")
public class BetEntity {
    private int id;
    private String uuidLot;
    private String bulkBet;

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
    @Column(name = "uuid_lot", nullable = false, length = -1)
    public String getUuidLot() {
        return uuidLot;
    }

    public void setUuidLot(String uuidLot) {
        this.uuidLot = uuidLot;
    }

    @Basic
    @Column(name = "bulk_bet", nullable = true, length = -1)
    public String getBulkBet() {
        return bulkBet;
    }

    public void setBulkBet(String bulkBet) {
        this.bulkBet = bulkBet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BetEntity betEntity = (BetEntity) o;

        if (id != betEntity.id) return false;
        if (uuidLot != null ? !uuidLot.equals(betEntity.uuidLot) : betEntity.uuidLot != null) return false;
        if (bulkBet != null ? !bulkBet.equals(betEntity.bulkBet) : betEntity.bulkBet != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uuidLot != null ? uuidLot.hashCode() : 0);
        result = 31 * result + (bulkBet != null ? bulkBet.hashCode() : 0);
        return result;
    }
}
