package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bet", schema = "public", catalog = "auction")
public class BetEntity implements Serializable {
    private static final long serialVersionUID = 1;

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
        return id == betEntity.id &&
                Objects.equals(uuid, betEntity.uuid) &&
                Objects.equals(bulk, betEntity.bulk);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uuid, bulk);
    }
}
