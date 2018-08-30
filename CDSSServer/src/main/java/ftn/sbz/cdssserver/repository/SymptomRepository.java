package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.sickness.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    Symptom findById(long id);

    Symptom findByName(String name);
}
