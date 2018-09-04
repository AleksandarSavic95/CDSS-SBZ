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
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    // insert all sicknesses (and RuleService to use it in rules)
    private void initializeSession() {
        //KieSession kieSession = KieSessionService.getSession(SecurityUtils.getUsernameOfLoggedUser());// F
        kieSession.insert(this); // here or in KieSessionService???

        Collection<?> insertedSicknesses = kieSession.getObjects(new ClassObjectFilter(Sickness.class));
        System.out.println("# of sicknesses before insert: " + insertedSicknesses.size());

        // TODO: this way or to put @Expire(1m) on Sickness? That needs a Date property in Sickness for @Timestamp :( :(
//        if (insertedSicknesses.size() == 0) {
            final List<Sickness> sicknesses = sicknessService.findAll();
            System.out.println("\ninserted: " + sicknesses.size() + " sicknesses!\n");
            sicknesses.forEach(kieSession::insert);
//        }
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
//        List<PossibleSickness> possibleSicknesses = findPossibleSicknesses(id, diagnosisDto);
//        if (possibleSicknesses == null)
//            return null;
//        PossibleSickness mostProbable = possibleSicknesses.iterator().next();
        QueryResults results = findPossibleSicknesses(id, diagnosisDto);
        System.out.println("results: " + results);
        assert results != null;
        //assert results.iterator().hasNext();
        Iterator<QueryResultsRow> iterator = results.iterator();
        System.out.println("iterator: " + iterator);

        if (iterator.hasNext()) {
//            return (PossibleSickness) results.iterator().next().get("$diagnosis");
//        }
            PossibleSickness mostProbable = (PossibleSickness) iterator.next().get("$diagnosis");
            System.out.println("::::::::: most probable sickness ::::::::::::::");
            System.out.println(mostProbable.getSickness().getName() + " " + mostProbable.getPercentage());

            while(iterator.hasNext()) {
                PossibleSickness lessProbable = (PossibleSickness) iterator.next().get("$diagnosis");

                System.out.println("::::::::: LESS probable sickness ::::::::::::::");
                System.out.println(lessProbable.getSickness().getName() + " " + lessProbable.getPercentage());
            }
            return mostProbable;
        }
        System.out.println("iterator was empty!");
        return null;
    }

    @Override
    public List<PossibleSickness> getPossibleSicknesses(long id, DiagnosisDto diagnosisDto) {
        QueryResults results = findPossibleSicknesses(id, diagnosisDto);
        assert results != null;

        if (results.iterator().hasNext())
            return (List<PossibleSickness>) results.iterator().next().get("$possibleSicknesses");
        return null;
    }

    private QueryResults findPossibleSicknesses(long id, DiagnosisDto diagnosisDto) {
        Patient patient = patientService.findById(id);
        if (patient == null)
            return null;

        initializeSession();

        patient.getTreatments().forEach(kieSession::insert);
        kieSession.insert(patient);
        System.out.println("checking in session: " + kieSession.getIdentifier());

        Collection<?> insertedSymptoms = kieSession.getObjects(new ClassObjectFilter(Symptom.class));
        System.out.println("# of symptoms before insert: " + insertedSymptoms.size());

        List<FactHandle> factHandles = new ArrayList<>();
        diagnosisDto.getSymptoms().forEach(s -> {
            final Symptom symptom = symptomService.findById(s.getId());
            if (symptom != null)
                factHandles.add(kieSession.insert(symptom));
        });
        factHandles.add(kieSession.insert(diagnosisDto));

        // check patient's temperature, add "complex" symptoms and diagnose the patient
        kieSession.getAgenda().getAgendaGroup("diagnosis").setFocus();
        kieSession.getAgenda().getAgendaGroup("complex symptoms").setFocus();
        kieSession.getAgenda().getAgendaGroup("temperature").setFocus();
        // "temperature" rules are on top of the stack, then "complex symptoms", then "diagnosis"
        kieSession.fireAllRules();


        // // old way - problem with multiple same PossibleSicknesses objects
        // // NEW NEW NEW - not a problem after deletion of fact handles was implemented :)
//        Collection<?> possibleSicknesses = kieSession.getObjects(new ClassObjectFilter(PossibleSickness.class));
//
//        return possibleSicknesses.stream().map(s -> (PossibleSickness) s)
//                .sorted(Comparator.comparingDouble(PossibleSickness::getPercentage).reversed())
//                .collect(Collectors.toList());
        QueryResults result = kieSession.getQueryResults("getDiagnosis");

        insertedSymptoms = kieSession.getObjects(new ClassObjectFilter(Symptom.class));
        System.out.println("# of symptoms AFTER insertions AND RULES FIRED: " + insertedSymptoms.size());
        // clear memory of Symptoms and Diagnosis DTOs for next diagnosis to be valid
        // ONLY if not using    @Event  +  window:time  !!!
//        for( Object object: insertedSymptoms ){
//            kieSession.delete(kieSession.getFactHandle(object));
//        }
//        for( Object object: kieSession.getObjects() ){
//            kieSession.delete(kieSession.getFactHandle(object));
//        }
        return result;
    }

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
