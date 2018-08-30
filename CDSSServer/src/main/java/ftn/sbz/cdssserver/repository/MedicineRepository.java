package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Medicine findById(long id);

    Medicine findByName(String name);
}
