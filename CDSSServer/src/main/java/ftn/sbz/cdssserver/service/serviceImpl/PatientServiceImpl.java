package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.repository.PatientRepository;
import ftn.sbz.cdssserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Page<Patient> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public Patient findById(long id) {
        return patientRepository.findById(id);
    }

    public Patient findByMedicalCardNumber(String medicalCardNumber) {
        return patientRepository.findByMedicalCardNumber(medicalCardNumber);
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient update(long id, Patient patient) {
        final Patient persisted = findById(id);
        persisted.setName(patient.getName());

        persisted.setSickFrom(patient.getSickFrom());
        persisted.setAllergensMedicines(patient.getAllergensMedicines());
        persisted.setAllergensIngredients(patient.getAllergensIngredients());

        return patientRepository.save(persisted);
    }

    public void delete(long id) {
        final Patient persisted = findById(id);
        persisted.getTreatments().forEach(t -> t.setPatient(null));

        patientRepository.delete(persisted);
    }

}
