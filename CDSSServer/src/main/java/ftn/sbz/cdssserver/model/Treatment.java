package ftn.sbz.cdssserver.model;

import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import javax.persistence.*;
import java.util.*;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
@Entity
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "sickness_id")
    private Sickness sickness;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "treatment_medicine",
            joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"))
    private List<Medicine> medicines;

    public Treatment() {
        this.timestamp = new Date();
        this.medicines = new ArrayList<>();
    }

    public Treatment(Date timestamp, Doctor doctor, Patient patient, Sickness sickness, List<Medicine> medicines) {
        this.timestamp = timestamp;
        this.doctor = doctor;
        this.patient = patient;
        this.sickness = sickness;
        this.medicines = medicines;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public void setSickness(Sickness sickness) {
        this.sickness = sickness;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    // called from rules!
    public boolean hasAnalgetics() {
        return this.medicines.stream().anyMatch(medicine -> medicine.getType().name().equals("ANALGETIC"));
    }

    @Override
    public String toString() {
        return "Treatment {" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", sickness=" + sickness.getName() +
                ", # of medicines=" + medicines.size() +
                '}';
    }
}
