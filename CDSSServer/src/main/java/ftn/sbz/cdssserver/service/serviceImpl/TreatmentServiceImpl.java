package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.Treatment;
import ftn.sbz.cdssserver.repository.TreatmentRepository;
import ftn.sbz.cdssserver.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;

    @Autowired
    public TreatmentServiceImpl(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    public Page<Treatment> findAll(Pageable pageable) {
        return treatmentRepository.findAll(pageable);
    }

    public List<Treatment> findByPatient(Patient patient) {
        return treatmentRepository.findByPatient(patient);
    }

    public Treatment findById(long id) {
        return treatmentRepository.findById(id);
    }

    public Treatment create(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public void delete(long id) {
        final Treatment persisted = findById(id);
        treatmentRepository.delete(persisted);
    }
}
