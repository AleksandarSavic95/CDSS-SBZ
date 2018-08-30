package ftn.sbz.cdssserver.repository;

import ftn.sbz.cdssserver.model.medicine.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findById(long id);
    Ingredient findByName(String name);
}
