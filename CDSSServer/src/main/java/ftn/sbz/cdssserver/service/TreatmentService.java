package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TreatmentService {
    List<Treatment> findAll();

    Page<Treatment> findAll(Pageable pageable);

    List<Treatment> findByPatient(Patient patient);

    Treatment findById(long id);

    Treatment create(Treatment treatment);

    void delete(long id);
}
