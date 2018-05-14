package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "category", schema = "public", catalog = "auction")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = -5571327862025947109L;
    private int id;
    private String categoryName;
    private Collection<LotEntity> lotsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "category_name", nullable = false, length = -1)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @OneToMany(mappedBy = "categoryByIdCategory")
    public Collection<LotEntity> getLotsById() {
        return lotsById;
    }

    public void setLotsById(Collection<LotEntity> lotsById) {
        this.lotsById = lotsById;
    }
}
