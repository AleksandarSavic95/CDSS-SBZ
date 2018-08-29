package ftn.sbz.cdssserver.model.medicine;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    public Ingredient() {}

    public Ingredient(@NotEmpty String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient {" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
