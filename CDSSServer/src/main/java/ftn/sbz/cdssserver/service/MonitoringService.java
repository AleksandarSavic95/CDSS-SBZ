package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.monitoring.MonitoringPatient;
import ftn.sbz.cdssserver.model.sickness.Sickness;

import java.util.Collection;

public interface MonitoringService {
    Collection<MonitoringPatient> getAllOnIntensiveCare();

    boolean putPatientToIntensiveCare(Patient patient, Sickness sickness);

    boolean releasePatientFromIntensiveCare(long medicalCardNumber);
}
