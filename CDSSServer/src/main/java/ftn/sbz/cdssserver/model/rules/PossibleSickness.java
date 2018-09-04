package ftn.sbz.cdssserver.model.rules;

import ftn.sbz.cdssserver.model.sickness.Sickness;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("getDateDiagnosed")
public class PossibleSickness {

    private Sickness sickness;

    private double percentage;

    private Date dateDiagnosed;

    public PossibleSickness() {
        this.dateDiagnosed = new Date();
    }

    public PossibleSickness(Sickness sickness, double percentage) {
        this.sickness = sickness;
        this.percentage = percentage;
        this.dateDiagnosed = new Date();
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

    public Date getDateDiagnosed() {
        return dateDiagnosed;
    }

    public void setDateDiagnosed(Date dateDiagnosed) {
        this.dateDiagnosed = dateDiagnosed;
    }
}
