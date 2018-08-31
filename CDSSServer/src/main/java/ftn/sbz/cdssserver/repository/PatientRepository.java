package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findById(long id);

    Patient findByMedicalCardNumber(String ssn);
}
