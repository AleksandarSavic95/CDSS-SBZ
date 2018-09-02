package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.medicine.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicineService {
    List<Medicine> findAll();

    Page<Medicine> findAll(Pageable pageable);

    Medicine findById(long id);

    Medicine create(Medicine medicine);

    Medicine update(long id, Medicine medicine);

    void delete(long id);

    Page<Medicine> findByText(String text, PageRequest pageRequest);
}
