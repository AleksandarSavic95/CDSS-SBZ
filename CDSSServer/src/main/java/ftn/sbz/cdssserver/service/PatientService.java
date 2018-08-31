package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();

    Page<Patient> findAll(Pageable pageable);

    Patient findById(long id);

    Patient findByMedicalCardNumber(String medicalCardNumber);

    Patient create(Patient patient);

    Patient update(long id, Patient patient);

    void delete(long id);
}
