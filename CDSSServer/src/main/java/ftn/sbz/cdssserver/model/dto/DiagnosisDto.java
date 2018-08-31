package ftn.sbz.cdssserver.model.dto;

import ftn.sbz.cdssserver.model.sickness.Symptom;

import java.util.List;

public class DiagnosisDto {

    private List<Symptom> symptoms;

    private double temperature;

    public DiagnosisDto() {
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
