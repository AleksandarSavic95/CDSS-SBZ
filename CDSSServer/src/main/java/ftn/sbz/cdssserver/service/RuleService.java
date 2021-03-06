package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleAllergies;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RuleService {
    PossibleAllergies checkAllergies(long id, @RequestBody List<Medicine> medicines);

    Sickness findSicknessByName(String s);

    PossibleSickness getDiagnosedSickness(long id, DiagnosisDto diagnosisDto);

    List<PossibleSickness> getPossibleSicknesses(long id, DiagnosisDto diagnosisDto);

    // used in rules!
    void insertSymptom(String symptomName);

    int getRulesAmount();
}
