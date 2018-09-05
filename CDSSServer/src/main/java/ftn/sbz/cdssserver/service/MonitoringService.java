package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.monitoring.MonitoringTask;

import java.util.Collection;

public interface MonitoringService {
    Collection<MonitoringTask> getAllOnIntensiveCare();

    String putPatientToIntensiveCare(long patientId, Sickness sickness);

    String releasePatientFromIntensiveCare(long medicalCardNumber);
}
