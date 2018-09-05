package ftn.sbz.cdssserver.model.monitoring;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("15m")
public class OxygenLevel {
    private MonitoringPatient patient;
    private double level;

}
