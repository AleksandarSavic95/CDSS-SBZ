package ftn.sbz.cdssserver.model.rules;

import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;

import java.util.ArrayList;
import java.util.List;

public class PossibleAllergies {

    private List<Medicine> medicine;

    private List<Ingredient> ingredients;

    public PossibleAllergies() {
        this.medicine = new ArrayList<>();
        this.ingredients = new ArrayList<>();
    }

    public PossibleAllergies(List<Medicine> medicine, List<Ingredient> ingredients) {
        this.medicine = medicine;
        this.ingredients = ingredients;
    }

    public List<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<Medicine> medicine) {
        this.medicine = medicine;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
