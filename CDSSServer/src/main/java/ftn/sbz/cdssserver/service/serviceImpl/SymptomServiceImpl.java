package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.sickness.Symptom;
import ftn.sbz.cdssserver.repository.SymptomRepository;
import ftn.sbz.cdssserver.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository symptomRepository;

    @Autowired
    public SymptomServiceImpl(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public List<Symptom> findAll() {
        return symptomRepository.findAll();
    }

    public Page<Symptom> findAll(Pageable pageable) {
        return symptomRepository.findAll(pageable);
    }

    public Symptom findById(long id) {
        return symptomRepository.findById(id);
    }

    public Symptom findByName(String name) {
        return symptomRepository.findByName(name);
    }

    public Symptom create(Symptom symptom) {
        if (symptomRepository.findByName(symptom.getName()) != null)
            return null;
        return symptomRepository.save(symptom);
    }

    public Symptom update(long id, Symptom symptom) {
        final Symptom persisted = findById(id);
        if (persisted == null)
            return null;
        // New name, if changed, must not collide with existing symptoms
        if ((!symptom.getName().equals(persisted.getName())) && symptomRepository.findByName(symptom.getName()) != null)
            return null;

        persisted.setName(symptom.getName());
        return symptomRepository.save(persisted);
    }

    public void delete(long id) {
        final Symptom persisted = findById(id);
        if (persisted != null)
            symptomRepository.delete(persisted);
    }
}
