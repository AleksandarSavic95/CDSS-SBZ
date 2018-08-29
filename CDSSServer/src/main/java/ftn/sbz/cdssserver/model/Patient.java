package ftn.sbz.cdssserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.sickness.Sickness;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String medicalCardNumber;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patient_sickness",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sickness_id", referencedColumnName = "id"))
    private Set<Sickness> sickFrom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "allergen_medicine",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"))
    private Set<Medicine> allergensMedicines;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "allergen_ingredient",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> allergensIngredients;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @OrderBy(value = "timestamp desc") // in memory, NOT in db!
    private Set<Treatment> treatments;

    public Patient() {
        this.sickFrom = new HashSet<>();
        this.allergensMedicines = new HashSet<>();
        this.allergensIngredients = new HashSet<>();
        this.treatments = new HashSet<>();
    }

    public Patient(@NotEmpty String medicalCardNumber, @NotEmpty String name) {
        this();
        this.medicalCardNumber = medicalCardNumber;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMedicalCardNumber() {
        return medicalCardNumber;
    }

    public void setMedicalCardNumber(String medicalCardNumber) {
        this.medicalCardNumber = medicalCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sickness> getSickFrom() {
        return sickFrom;
    }

    public void setSickFrom(Set<Sickness> sickFrom) {
        this.sickFrom = sickFrom;
    }

    public Set<Medicine> getAllergensMedicines() {
        return allergensMedicines;
    }

    public void setAllergensMedicines(Set<Medicine> allergensMedicines) {
        this.allergensMedicines = allergensMedicines;
    }

    public Set<Ingredient> getAllergensIngredients() {
        return allergensIngredients;
    }

    public void setAllergensIngredients(Set<Ingredient> allergensIngredients) {
        this.allergensIngredients = allergensIngredients;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    @Override
    public String toString() {
        return "Patient {" +
                "id=" + id +
                ", medicalCardNumber='" + medicalCardNumber + '\'' +
                ", name='" + name + '\'' +
                ", # of sickFrom=" + sickFrom.size() +
                ", # of allergensMedicines=" + allergensMedicines.size() +
                ", # of allergensIngredients=" + allergensIngredients.size() +
                ", # of treatments=" + treatments.size() +
                '}';
    }
}
