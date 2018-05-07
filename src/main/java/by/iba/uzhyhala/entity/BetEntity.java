package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;

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
}
