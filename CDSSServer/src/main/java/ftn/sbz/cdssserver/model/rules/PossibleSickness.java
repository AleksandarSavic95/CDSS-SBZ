package ftn.sbz.cdssserver.model.rules;

import ftn.sbz.cdssserver.model.sickness.Sickness;

public class PossibleSickness {

    private Sickness sickness;

    private double percentage;

    public PossibleSickness() {
    }

    public PossibleSickness(Sickness sickness, double percentage) {
        this.sickness = sickness;
        this.percentage = percentage;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public void setSickness(Sickness sickness) {
        this.sickness = sickness;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
