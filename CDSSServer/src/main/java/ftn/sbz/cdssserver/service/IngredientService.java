package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.medicine.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Page<Ingredient> findAll(Pageable pageable);

    Ingredient findById(long id);

    Ingredient create(Ingredient ingredient);

    Ingredient update(long id, Ingredient ingredient);

    void delete(long id);

    Page<Ingredient> findByText(String text, PageRequest pageRequest);
}
