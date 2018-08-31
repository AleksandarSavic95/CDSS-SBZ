package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.sickness.Sickness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SicknessRepository extends JpaRepository<Sickness, Long> {

    Sickness findById(long id);

    Sickness findByName(String name);
}
