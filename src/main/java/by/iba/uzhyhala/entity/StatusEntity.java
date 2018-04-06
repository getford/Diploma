package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "status", schema = "public", catalog = "auction")
public class StatusEntity {
    private int id;
    private String name;
    private Collection<LotEntity> lotsById;

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
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntity that = (StatusEntity) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "statusByIdStatus")
    public Collection<LotEntity> getLotsById() {
        return lotsById;
    }

    public void setLotsById(Collection<LotEntity> lotsById) {
        this.lotsById = lotsById;
    }
}
