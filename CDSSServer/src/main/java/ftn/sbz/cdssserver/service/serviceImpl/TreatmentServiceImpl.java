package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Doctor;
import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.Treatment;
import ftn.sbz.cdssserver.repository.PatientRepository;
import ftn.sbz.cdssserver.repository.TreatmentRepository;
import ftn.sbz.cdssserver.repository.UserRepository;
import ftn.sbz.cdssserver.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public TreatmentServiceImpl(TreatmentRepository treatmentRepository, UserRepository userRepository, PatientRepository patientRepository) {
        this.treatmentRepository = treatmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
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
        treatment.setTimestamp(new Date());
        treatment.setDoctor((Doctor) userRepository.findByUsername(treatment.getDoctor().getUsername()));
        System.out.printf("<<< Doctor %s creating treatment for %s with %d analgetics.\n",
                treatment.getDoctor().getUsername(),
                treatment.getSickness().getName(),
                treatment.getMedicines().stream().filter(medicine -> medicine.getType().name().equals("ANALGETIC")).count());

        // add sickness to patient
        Patient patient = patientRepository.findById(treatment.getPatient().getId());
        patient.getSickFrom().add(treatment.getSickness());
        patient = patientRepository.save(patient);
        treatment.setPatient(patient);

        return treatmentRepository.save(treatment);
    }

    public void delete(long id) {
        final Treatment persisted = findById(id);
        treatmentRepository.delete(persisted);
    }
}
