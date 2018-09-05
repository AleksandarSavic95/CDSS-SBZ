package ftn.sbz.cdssserver.model.monitoring;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("10s")
public class HeartBeat {

    private MonitoringPatient patient;

    public HeartBeat() {
    }

    public HeartBeat(MonitoringPatient patient) {
        this.patient = patient;
    }

    public MonitoringPatient getPatient() {
        return patient;
    }

    public void setPatient(MonitoringPatient patient) {
        this.patient = patient;
    }
}
