package ftn.sbz.cdssserver.model.sickness;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sickness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @Position(0) // KIE-thing
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    // @NotEmpty
    @Column
    private int sicknessGroup;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sickness_general_symptom",
            joinColumns = @JoinColumn(name = "sickness_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "general_id", referencedColumnName = "id"))
    private Set<Symptom> generalSymptoms;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sickness_specific_symptom",
            joinColumns = @JoinColumn(name = "sickness_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "specific_id", referencedColumnName = "id"))
    private Set<Symptom> specificSymptoms;

    public Sickness() {
        this.generalSymptoms = new HashSet<>();
        this.specificSymptoms = new HashSet<>();
    }

    public Sickness(@NotEmpty String name) {
        this();
        this.name = name;
    }

    public Sickness(@NotEmpty String name, int sicknessGroup, Set<Symptom> generalSymptoms, Set<Symptom> specificSymptoms) {
        this.name = name;
        this.sicknessGroup = sicknessGroup;
        this.generalSymptoms = generalSymptoms;
        this.specificSymptoms = specificSymptoms;
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

    public int getSicknessGroup() {
        return sicknessGroup;
    }

    public void setSicknessGroup(int sicknessGroup) {
        this.sicknessGroup = sicknessGroup;
    }

    public Set<Symptom> getGeneralSymptoms() {
        return generalSymptoms;
    }

    public void setGeneralSymptoms(Set<Symptom> generalSymptoms) {
        this.generalSymptoms = generalSymptoms;
    }

    public Set<Symptom> getSpecificSymptoms() {
        return specificSymptoms;
    }

    public void setSpecificSymptoms(Set<Symptom> specificSymptoms) {
        this.specificSymptoms = specificSymptoms;
    }

    @Override
    public String toString() {
        return "Sickness {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sicknessGroup=" + sicknessGroup +
                ", # of generalSymptoms=" + generalSymptoms.size() +
                ", # of specificSymptoms=" + specificSymptoms.size() +
                '}';
    }
}
