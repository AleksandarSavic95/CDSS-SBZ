package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
    @Override
    public ResponseEntity checkAllergies(long id, List<Medicine> medicine) {
        return null;
    }

    @Override
    public List<PossibleSickness> getPossibleSicknesses(long id, DiagnosisDto diagnosisDto) {
        return null;
    }

    @Override
    public Object getDoctorSession() {
        return null;
    }
}
