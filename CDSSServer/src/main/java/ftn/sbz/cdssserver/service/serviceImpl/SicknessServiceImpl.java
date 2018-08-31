package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.repository.SicknessRepository;
import ftn.sbz.cdssserver.service.SicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SicknessServiceImpl implements SicknessService {

    private final SicknessRepository sicknessRepository;

    @Autowired
    public SicknessServiceImpl(SicknessRepository sicknessRepository) {
        this.sicknessRepository = sicknessRepository;
    }

    public List<Sickness> findAll() {
        return sicknessRepository.findAll();
    }

    public Page<Sickness> findAll(Pageable pageable) {
        return sicknessRepository.findAll(pageable);
    }

    public Sickness findById(long id) {
        return sicknessRepository.findById(id);
    }

    public Sickness findByName(String name) {
        return sicknessRepository.findByName(name);
    }

    public Sickness create(Sickness sickness) {
        if (sicknessRepository.findByName(sickness.getName()) != null)
            return null;
        return sicknessRepository.save(sickness);
    }

    public Sickness update(long id, Sickness sickness) {
        final Sickness persisted = findById(id);
        if (persisted == null)
            return null;
        if (sickness.getName() != null) {
            // New name must not collide with existing sicknesses
            if (sicknessRepository.findByName(sickness.getName()) != null)
                return null;
            persisted.setName(sickness.getName());
        }
        if (sickness.getGeneralSymptoms() != null)
            persisted.setGeneralSymptoms(sickness.getGeneralSymptoms());
        if (sickness.getSpecificSymptoms() != null)
            persisted.setSpecificSymptoms(sickness.getSpecificSymptoms());
        if (sickness.getSicknessGroup() != 0)
            persisted.setSicknessGroup(sickness.getSicknessGroup());

        return sicknessRepository.save(persisted);
    }

    public void delete(long id) {
        final Sickness persisted = findById(id);
        if (persisted != null)
            sicknessRepository.delete(persisted);
    }
}
