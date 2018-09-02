package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.repository.IngredientRepository;
import ftn.sbz.cdssserver.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Ingredient findById(long id) {
        return ingredientRepository.findById(id);
    }


    public Ingredient create(Ingredient ingredient) {
        if (ingredientRepository.findByName(ingredient.getName()) != null)
            return null;
        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(long id, Ingredient ingredient) {
        final Ingredient persisted = findById(id);
        if (persisted == null)
            return null;
        // New name must not collide with existing ingredients
        if (ingredientRepository.findByName(ingredient.getName()) != null)
            return null;
        persisted.setName(ingredient.getName());

        return ingredientRepository.save(persisted);
    }

    public void delete(long id) {
        final Ingredient persisted = findById(id);
        if (persisted != null)
            ingredientRepository.delete(persisted);
    }

    @Override
    public Page<Ingredient> findByText(String text, PageRequest pageRequest) {
        return ingredientRepository.findByNameContainingIgnoreCase(text.toLowerCase(), pageRequest);
    }
}
