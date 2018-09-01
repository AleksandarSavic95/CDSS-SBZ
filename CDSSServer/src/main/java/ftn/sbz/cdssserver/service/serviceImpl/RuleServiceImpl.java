package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleAllergies;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.service.*;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private PatientService patientService;
    private MedicineService medicineService;

    private SicknessService sicknessService; // А А А А А А А А А
    private KieSession kieSession; // А А А А А А А А А

    @Autowired
    public RuleServiceImpl(SicknessService sicknessService, KieSession kieSession, PatientService patientService, MedicineService medicineService) {
        this.sicknessService = sicknessService; // А А А А А А А А А
        this.kieSession = kieSession; // А А А А А А А А А
        this.patientService = patientService;
        this.medicineService = medicineService;
    }

    // insert all sicknesses (and RuleService fact to target specific rules)
    private void initializeSession() {
        //KieSession kieSession = KieSessionService.getSession(SecurityUtils.getUsernameOfLoggedUser());// F
        kieSession.insert(this);

        final List<Sickness> sicknesses = sicknessService.findAll();
        sicknesses.forEach(kieSession::insert);
    }

    @Override // F F F F F List<PossibleAllergies>
    public PossibleAllergies checkAllergies(long id, List<Medicine> medicines) {
        final Patient patient = patientService.findById(id);
        if (patient == null)
            return null;
        kieSession.insert(patient);

        medicines.forEach(m -> {
            final Medicine medicine = medicineService.findById(m.getId());
            kieSession.insert(medicine);
            medicine.getIngredients().forEach(kieSession::insert);
        });

        // F F F F F F F F F F F F F F F F F F F F F
//        kieSession.getAgenda().getAgendaGroup("allergies").setFocus();
//        kieSession.fireAllRules();
//
//        final Collection<?> allergies = kieSession.getObjects(new ClassObjectFilter(PossibleAllergies.class));
//
//        // F F F F F    kod nje ide "return allergies", jer joj ova metoda vraca --> List<PossibleAllergies>
//        return allergies.stream().map(a -> (PossibleAllergies) a).collect(Collectors.toList());
        QueryResultsRow result = kieSession.getQueryResults("checkAllergies", patient).iterator().next();

        return new PossibleAllergies(
                (List<Medicine>) result.get("$medAllergens"),
                (List<Ingredient>) result.get("$ingAllergens"));
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
