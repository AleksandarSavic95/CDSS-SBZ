package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.medicine.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Medicine findById(long id);

    Medicine findByName(String name);

    Page<Medicine> findByNameContainingIgnoreCase(String text, Pageable pageable);
}
