package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.Treatment;
import ftn.sbz.cdssserver.model.medicine.Medicine;
import ftn.sbz.cdssserver.model.reports.ChronicSickness;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.service.*;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private SicknessService sicknessService;

    @Autowired
    private MedicineService medicineService;

    @Override
    public List<ChronicSickness> getChronicallySickPatients() {
        initializeSession();
        QueryResults queryResults = kieSession.getQueryResults("chronicallySickPatients");

        ArrayList<ChronicSickness> resultsList = new ArrayList<>();
        queryResults.iterator().forEachRemaining(queryResult -> resultsList.add(new ChronicSickness((Sickness) queryResult.get("$s"), (Patient) queryResult.get("$p"))));
        return resultsList;
    }

    @Override
    public List<Patient> getAddicts() {
        return getPatients("addicts");
    }

    @Override
    public List<Patient> getWeakImmunityPatients() {
        return getPatients("weakImmunityPatients");
    }

    private List<Patient> getPatients(String queryName) {
        initializeSession();
        QueryResults queryResults = kieSession.getQueryResults(queryName);

        ArrayList<Patient> resultsList = new ArrayList<>();
        queryResults.iterator().forEachRemaining(queryResult -> resultsList.add((Patient) queryResult.get("$p")));
        return resultsList;
    }

    /**
     * Add Patients, Treatments, Sicknesses and Medicines to session
     */
    private void initializeSession() {
        final List<Patient> patients = patientService.findAll();
        final List<Treatment> treatments = treatmentService.findAll();
        final List<Sickness> sicknesses = sicknessService.findAll();
        final List<Medicine> medicines = medicineService.findAll();

        patients.forEach(kieSession::insert);
        treatments.forEach(kieSession::insert);
        sicknesses.forEach(kieSession::insert);
        medicines.forEach(kieSession::insert);
    }
}
