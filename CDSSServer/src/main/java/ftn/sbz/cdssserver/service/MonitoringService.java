package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.monitoring.MonitoringTask;

import java.util.Collection;

public interface MonitoringService {
    Collection<MonitoringTask> getAllOnIntensiveCare();

    String putPatientToIntensiveCare(Patient patient, Sickness sickness);

    String releasePatientFromIntensiveCare(long medicalCardNumber);

    void notifyDoctor(String message);
}
