package ftn.sbz.cdssserver.monitoring;

import ftn.sbz.cdssserver.model.monitoring.MonitoringPatient;
import ftn.sbz.cdssserver.model.monitoring.OxygenLevel;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.Date;

public class MonitoringTask implements Runnable {

    private MonitoringPatient patient;
    private boolean isMonitored;

    private KieSession kieSession;

    private FactHandle patientHandle;

    public MonitoringTask() {
        this.isMonitored = true;
        System.out.println("MT() CREATED at" + new Date());
    }

    @Override
    public void run() {
        System.out.println("RUN at" + new Date());

        System.out.println("\npatient: " + patient.getPatient().getName());
        System.out.println("\nsickness: " + patient.getSickness().getName());
        System.out.println("inserting patient into kieSession..");

        this.patientHandle = kieSession.insert(patient);
        System.out.println("LOOP..... uncomment when not testing");
        while(this.isMonitored) {
            System.out.printf("Patient %s is being monitored..\n", patient.getPatient().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("FIRING RULES....");
            fireRules();
        }
        System.out.printf("Patient %s released from monitoring.\n", patient.getPatient().getName());
    }

    private int fireRules() {
        System.out.println("INTENSIVE CARE SERVICE FIRE_RULES()");
        kieSession.getAgenda().getAgendaGroup("monitoring").setFocus();
        return kieSession.fireAllRules();
    }

    public int changeOxygenLevel(double newLevel) {
        boolean growth = newLevel > patient.getOxygenLevel().getLevel();
        final OxygenLevel oxygenLevel = new OxygenLevel(patient, newLevel, growth);

        patient.setOxygenLevel(oxygenLevel);

        kieSession.insert(oxygenLevel);
        kieSession.update(patientHandle, patient);

        return fireRules();
    }

//    public int addHeartBeat() {
//        final HeartBeat heartBeat = new HeartBeat(patient);
//        kieSession.insert(heartBeat);
//        return fireRules();
//    }
//
//    public int addUrination(float amount) {
//        final Urination urination = new Urination(patient, amount);
//        kieSession.insert(urination);
//        return fireRules();
//    }

    public MonitoringPatient getPatient() {
        return patient;
    }

    public void setPatient(MonitoringPatient patient) {
        this.patient = patient;
    }

    public void setMonitored(boolean monitored) {
        isMonitored = monitored;
    }

    public void setKieSession(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    public FactHandle getPatientHandle() {
        return patientHandle;
    }

    public void setPatientHandle(FactHandle patientHandle) {
        this.patientHandle = patientHandle;
    }
}
