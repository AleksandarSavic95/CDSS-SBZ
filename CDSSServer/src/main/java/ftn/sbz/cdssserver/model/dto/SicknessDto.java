package ftn.sbz.cdssserver.model.dto;

import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.model.sickness.Symptom;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SicknessDto {

    @NotEmpty
    private String name;

    private List<Symptom> generalSymptoms;

    private List<Symptom> specificSymptoms;

    private int sicknessGroup;

    public SicknessDto() {
    }

    public Sickness createSickness() {
        final Sickness sickness = new Sickness(name);
        if (generalSymptoms != null)
            sickness.getGeneralSymptoms().addAll(generalSymptoms);
        else
            sickness.setGeneralSymptoms(null);

        if (specificSymptoms != null)
            sickness.getSpecificSymptoms().addAll(specificSymptoms);
        else
            sickness.setSpecificSymptoms(null);

        sickness.setSicknessGroup(sicknessGroup);

        return sickness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Symptom> getGeneralSymptoms() {
        return generalSymptoms;
    }

    public void setGeneralSymptoms(List<Symptom> generalSymptoms) {
        this.generalSymptoms = generalSymptoms;
    }

    public List<Symptom> getSpecificSymptoms() {
        return specificSymptoms;
    }

    public void setSpecificSymptoms(List<Symptom> specificSymptoms) {
        this.specificSymptoms = specificSymptoms;
    }

    public int getSicknessGroup() {
        return sicknessGroup;
    }

    public void setSicknessGroup(int sicknessGroup) {
        this.sicknessGroup = sicknessGroup;
    }
}
