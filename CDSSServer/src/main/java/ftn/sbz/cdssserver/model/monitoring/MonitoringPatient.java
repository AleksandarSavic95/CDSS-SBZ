package ftn.sbz.cdssserver.model.monitoring;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.sickness.Sickness;

public class MonitoringPatient {
    private Patient patient;
    private Sickness sickness;
    private OxygenLevel oxygenLevel;

    public MonitoringPatient() {
    }

    public MonitoringPatient(Patient patient, Sickness sickness) {
        this.patient = patient;
        this.sickness = sickness;
    }

    public MonitoringPatient(Patient patient, Sickness sickness, OxygenLevel oxygenLevel) {
        this.patient = patient;
        this.sickness = sickness;
        this.oxygenLevel = oxygenLevel;
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

    public OxygenLevel getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(OxygenLevel oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }
}
