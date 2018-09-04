package ftn.sbz.cdssserver.model.sickness;

import org.kie.api.definition.type.Role;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Role(Role.Type.EVENT)
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    // TODO: add sets "generalFor Sickness" and "specificFor Sickness" ???
    // TO-DO: add sets "generalFor Sickness" and "specificFor Sickness" ???

    public Symptom() {}

    public Symptom(@NotEmpty String name) {
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
        return "Symptom {" + "id=" + id + ", name='" + name + "'}";
    }
}
