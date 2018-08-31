package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    Treatment findById(long id);

    List<Treatment> findByPatient(Patient patient);
}
