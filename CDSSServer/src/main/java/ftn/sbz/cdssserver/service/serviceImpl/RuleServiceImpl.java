package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.security.SecurityUtils;
import ftn.sbz.cdssserver.service.KieSessionService;
import ftn.sbz.cdssserver.service.RuleService;
import ftn.sbz.cdssserver.service.SicknessService;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    // А А А А А А А А А А А А А А А А А А А А А А А А
    private SicknessService sicknessService;
    private KieSession kieSession;

    @Autowired
    public RuleServiceImpl(SicknessService sicknessService, KieSession kieSession) { // А А А А
        this.sicknessService = sicknessService;
        this.kieSession = kieSession;
    }
    // А А А А А А А А А А А А А А А А А А А А А А А А

    // insert all sicknesses (and RuleService fact to target specific rules)
    private void initializeSession() {
        //KieSession kieSession = KieSessionService.getSession(SecurityUtils.getUsernameOfLoggedUser());// F
        kieSession.insert(this);

        final List<Sickness> sicknesses = sicknessService.findAll();
        sicknesses.forEach(kieSession::insert);
    }

    @Override
    public ResponseEntity checkAllergies(long id, List<Medicine> medicine) {
        return null;
    }

    @Override
    public List<PossibleSickness> getPossibleSicknesses(long id, DiagnosisDto diagnosisDto) {
        initializeSession();
        return null;
    }

    @Override
    public int getRulesAmount() {
        initializeSession();
        KieBase kieBase = kieSession.getKieBase();
        int count = 0;
        for (KiePackage p: kieBase.getKiePackages()) {
            count += p.getRules().size();
        }
        return count;
    }


}
