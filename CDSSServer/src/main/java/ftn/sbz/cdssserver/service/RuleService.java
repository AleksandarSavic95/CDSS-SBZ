package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RuleService {
    ResponseEntity checkAllergies(long id, @RequestBody List<Medicine> medicine);

    List<PossibleSickness> getPossibleSicknesses(long id, DiagnosisDto diagnosisDto);

    int getRulesAmount();
}
