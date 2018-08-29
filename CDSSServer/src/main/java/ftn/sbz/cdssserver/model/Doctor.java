package ftn.sbz.cdssserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Doctor extends User {
    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Treatment> treatments;

    public Doctor() {
        super();
        this.role = Role.DOCTOR;
    }

    public Doctor(@NotEmpty String name, @NotEmpty String username, @NotEmpty String password) {
        super(name, username, password);
        this.role = Role.DOCTOR;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    @Override
    public String toString() {
        return super.toString() + " # of treatments=" + treatments.size();
    }
}
