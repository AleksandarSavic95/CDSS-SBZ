package ftn.sbz.cdssserver.model.dto;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.Treatment;
import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.sickness.Sickness;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatientDto {

    private long id;

    private String medicalCardNumber;

    private String name;

    private Set<Sickness> sickFrom;

    private Set<Medicine> allergensMedicines;

    private Set<Ingredient> allergensIngredients;

    private List<Treatment> treatments;

    public PatientDto() {
    }

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.medicalCardNumber = patient.getMedicalCardNumber();
        this.name = patient.getName();
        this.sickFrom = patient.getSickFrom();

        this.allergensMedicines = patient.getAllergensMedicines();
        this.allergensIngredients = patient.getAllergensIngredients();

        this.treatments = new ArrayList<>(patient.getTreatments());
        this.treatments.forEach(t -> t.setPatient(null));

        this.treatments.sort((t1, t2) -> t1.getTimestamp().after(t2.getTimestamp()) ? -1 : 1);
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

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }
}
