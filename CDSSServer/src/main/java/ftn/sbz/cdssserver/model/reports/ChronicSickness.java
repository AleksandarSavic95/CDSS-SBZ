package ftn.sbz.cdssserver.model.reports;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.sickness.Sickness;

public class ChronicSickness {
    private Sickness sickness;
    private Patient patient;

    public ChronicSickness() {
    }

    public ChronicSickness(Sickness sickness, Patient patient) {
        this.sickness = sickness;
        this.patient = patient;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public void setSickness(Sickness sickness) {
        this.sickness = sickness;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
