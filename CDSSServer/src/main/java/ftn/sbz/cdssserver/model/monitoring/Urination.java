package ftn.sbz.cdssserver.model.monitoring;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class Urination {

    private MonitoringPatient patient;

    private double amount;

    public Urination() {
    }

    public Urination(MonitoringPatient patient, double amount) {
        this.patient = patient;
        this.amount = amount;
    }

    public MonitoringPatient getPatient() {
        return patient;
    }

    public void setPatient(MonitoringPatient patient) {
        this.patient = patient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
