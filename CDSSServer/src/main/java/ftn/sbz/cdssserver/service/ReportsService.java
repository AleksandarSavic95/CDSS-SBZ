package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.reports.ChronicSickness;

import java.util.List;

public interface ReportsService {

    List<ChronicSickness> getChronicallySickPatients();

    List<Patient> getAddicts();

    List<Patient> getWeakImmunityPatients();
}
