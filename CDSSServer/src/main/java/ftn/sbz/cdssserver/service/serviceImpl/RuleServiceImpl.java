package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.dto.DiagnosisDto;
import ftn.sbz.cdssserver.model.medicine.Ingredient;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.rules.PossibleAllergies;
import ftn.sbz.cdssserver.model.rules.PossibleSickness;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.model.sickness.Symptom;
import ftn.sbz.cdssserver.service.*;
import org.drools.core.ClassObjectFilter;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private PatientService patientService;
    private MedicineService medicineService;
    private SymptomService symptomService;

    private SicknessService sicknessService; // А А А А А А А А А
    private KieSession kieSession; // А А А А А А А А А

    @Autowired
    public RuleServiceImpl(SicknessService sicknessService, KieSession kieSession, PatientService patientService, MedicineService medicineService, SymptomService symptomService) {
        this.patientService = patientService;
        this.medicineService = medicineService;
        this.symptomService = symptomService;
        this.sicknessService = sicknessService; // А А А А А А А А А
        this.kieSession = kieSession; // А А А А А А А А А
    }

    /**
     * Insert all sicknesses (and RuleService to use it in rules)
     */
    private void initializeSession() {
        // F //KieSession kieSession = KieSessionService.getSession(SecurityUtils.getUsernameOfLoggedUser()); // F
        kieSession.insert(this); // A A A A A A A

        final List<Sickness> sicknesses = sicknessService.findAll();
        System.out.println("\ninserted: " + sicknesses.size() + " sicknesses!\n");
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
    public PossibleSickness getDiagnosedSickness(long id, DiagnosisDto diagnosisDto) {
        if (!reasonPossibleSicknesses(id, diagnosisDto))
            return null;

        // more dangerous sicknesses have bigger priority (FalsePositive better than FalseNegative)
        QueryResults results = kieSession.getQueryResults("getDiagnosis", 3);
        //  ! <--- MIND THE "NOT"!
        if (! results.iterator().hasNext()) {

            results = kieSession.getQueryResults("getDiagnosis", 2);
            if (! results.iterator().hasNext()) {
                results = kieSession.getQueryResults("getDiagnosis", 1);
                if (! results.iterator().hasNext()) {
                    System.out.println("\nNO DIAGNOSIS!\n");
                    return new PossibleSickness();
                }
            }
        }

        return (PossibleSickness) results.iterator().next().get("$D");
    }

    @Override
    public List<PossibleSickness> getPossibleSicknesses(long id, DiagnosisDto diagnosisDto) {
        if (!reasonPossibleSicknesses(id, diagnosisDto))
            return null;
        QueryResults results = kieSession.getQueryResults("getPossibleSicknesses");

        if (results.iterator().hasNext()) { // POSSIBLE SICKNESSES
            List<PossibleSickness> resultList = (List<PossibleSickness>) results.iterator().next()
                    .get("$possibleSicknesses");
            resultList.sort(Comparator.comparingDouble(PossibleSickness::getPercentage).reversed());
            return resultList;
        }

        System.out.println("iterator was empty!");
        return new ArrayList<>();
    }

    private boolean reasonPossibleSicknesses(long id, DiagnosisDto diagnosisDto) {
        Patient patient = patientService.findById(id);
        if (patient == null)
            return false;

        initializeSession();

        patient.getTreatments().forEach(kieSession::insert);
        kieSession.insert(patient);
        System.out.println("checking in session: " + kieSession.getIdentifier());

        Collection<?> insertedSymptoms = kieSession.getObjects(new ClassObjectFilter(Symptom.class));
        System.out.println("# of symptoms before insert: " + insertedSymptoms.size());

        diagnosisDto.getSymptoms().forEach(s -> {
            final Symptom symptom = symptomService.findById(s.getId());
            if (symptom != null)
                kieSession.insert(symptom);
        });
        kieSession.insert(diagnosisDto);

        // check patient's temperature, add "complex" symptoms and diagnose the patient
        kieSession.getAgenda().getAgendaGroup("diagnosis").setFocus();
        kieSession.getAgenda().getAgendaGroup("complex symptoms").setFocus();
        kieSession.getAgenda().getAgendaGroup("temperature").setFocus();
        // "temperature" rules are on top of the stack, then "complex symptoms", then "diagnosis"
        kieSession.fireAllRules();

        return true;
    }

    // F F F F F F F F F F F F F F F F F F F   |  ONLY if not using    @Event  +  window:time
    // clear memory of Symptoms and Diagnosis DTOs for next diagnosis to be valid
//    private void clearSession() {
//        for( Object object: kieSession.getObjects() ){
//            kieSession.delete(kieSession.getFactHandle(object));
//        } // F F F F F F F F F F F F F F F F F
//    }

    @Override
    public void insertSymptom(String symptomName) {
        Symptom foundSymptom = symptomService.findByName(symptomName);
        if (foundSymptom == null)
            return;
        kieSession.insert(foundSymptom);
    }

    @Override
    public Sickness findSicknessByName(String name) {
        initializeSession();
        QueryResults results = kieSession.getQueryResults("findSickness", name.toLowerCase());

        if (results.iterator().hasNext())
            return (Sickness) results.iterator().next().get("$found");
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
