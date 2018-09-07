package ftn.sbz.cdssserver.model.monitoring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("15m")
public class OxygenLevel {
    @JsonIgnore
    private MonitoringPatient patient;
    private double level;
    private boolean growth;

    public OxygenLevel(MonitoringPatient patient, double level, boolean growth) {
        this.patient = patient;
        this.level = level;
        this.growth = growth;
    }

    public MonitoringPatient getPatient() {
        return patient;
    }

    public void setPatient(MonitoringPatient patient) {
        this.patient = patient;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public boolean isGrowth() {
        return growth;
    }

    public void setGrowth(boolean growth) {
        this.growth = growth;
    }

}
