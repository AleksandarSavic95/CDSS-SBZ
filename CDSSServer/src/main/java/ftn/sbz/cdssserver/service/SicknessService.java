package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.sickness.Sickness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SicknessService {
    Page<Sickness> findAll(Pageable pageable);

    List<Sickness> findAll();

    Sickness findById(long id);

    Sickness findByName(String name);

    Sickness create(Sickness sickness);

    Sickness update(long id, Sickness sickness);

    void delete(long id);
}
