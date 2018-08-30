package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.repository.IngredientRepository;
import ftn.sbz.cdssserver.repository.MedicineRepository;
import ftn.sbz.cdssserver.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository, IngredientRepository ingredientRepository) {
        this.medicineRepository = medicineRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    public Page<Medicine> findAll(Pageable pageable) {
        return medicineRepository.findAll(pageable);
    }

    public Medicine findById(long id) {
        return medicineRepository.findById(id);
    }

    public Medicine create(Medicine medicine) {
        if (medicineRepository.findByName(medicine.getName()) != null)
            return null;
        Set<Ingredient> persistentIngredients = getPersistentIngredients(medicine.getIngredients());
        if (persistentIngredients == null) {
            return null;
        }
        medicine.setIngredients(persistentIngredients);

        return medicineRepository.save(medicine);
    }

    public Medicine update(long id, Medicine medicine) {
        final Medicine persisted = findById(id);
        if (persisted == null)
            return null;
        // New name must not collide with existing medicines
        if (medicineRepository.findByName(medicine.getName()) != null)
            return null;

        // All ingredients must exist in the DB
        Set<Ingredient> persistentIngredients = getPersistentIngredients(medicine.getIngredients());
        if (persistentIngredients == null)
            return null;

        persisted.setName(medicine.getName());
        persisted.setType(medicine.getType());
        persisted.setIngredients(persistentIngredients);
        return medicineRepository.save(persisted);
    }

    private Set<Ingredient> getPersistentIngredients(Set<Ingredient> ingredients) {
        Ingredient ingredient;
        Set<Ingredient> persistentIngredients = new HashSet<>();
        for (Ingredient ing : ingredients) {
            ingredient = ingredientRepository.findByName(ing.getName());
            System.out.println("ingredient: " + ing.getName() + " not null: " + (ingredient != null));
            if (ingredient == null)
                return null;
            persistentIngredients.add(ingredient);
        }
        return persistentIngredients;
    }

    public void delete(long id) {
        final Medicine persisted = findById(id);
        if (persisted != null)
            medicineRepository.delete(persisted);
    }
}
