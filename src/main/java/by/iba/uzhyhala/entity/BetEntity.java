package by.iba.uzhyhala.entity;

import javax.persistence.*;

@Entity
@Table(name = "bet", schema = "public", catalog = "auction")
public class BetEntity {
    private int id;
    private String uuid;
    private String bulk;

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
    @Column(name = "bulk", nullable = true, length = -1)
    public String getBulk() {
        return bulk;
    }

    public void setBulk(String bulk) {
        this.bulk = bulk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BetEntity betEntity = (BetEntity) o;

        if (id != betEntity.id) return false;
        if (uuid != null ? !uuid.equals(betEntity.uuid) : betEntity.uuid != null) return false;
        if (bulk != null ? !bulk.equals(betEntity.bulk) : betEntity.bulk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (bulk != null ? bulk.hashCode() : 0);
        return result;
    }
}
