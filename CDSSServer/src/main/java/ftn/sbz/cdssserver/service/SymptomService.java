package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.sickness.Symptom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SymptomService {

    List<Symptom> findAll();

    Page<Symptom> findAll(Pageable pageable);

    Symptom findById(long id);

    Symptom findByName(String name);

    Symptom create(Symptom symptom);

    Symptom update(long id, Symptom symptom);

    void delete(long id);
}
