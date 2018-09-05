package ftn.sbz.cdssserver.monitoring;

import ftn.sbz.cdssserver.model.monitoring.MonitoringPatient;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Scope("prototype") // create new component every time it is needed
public class MonitoringTask implements Runnable {

    private MonitoringPatient patient;
    private boolean isMonitored;

    @Autowired
    @Qualifier("monitoring")
    private KieSession kieSession;

    public MonitoringTask() {
        this.isMonitored = true;
        System.out.println("MT() CREATED at" + new Date());
    }

    public MonitoringTask(MonitoringPatient patient) {
        this.patient = patient;
        this.isMonitored = true;
        System.out.println("\nMT(MP) CREATED at" + new Date());
    }

    @Override
    public void run() {
        System.out.println("RUN at" + new Date());

        System.out.println("\npatient: " + patient.getPatient().getName());
        System.out.println("\nsickness: " + patient.getSickness().getName());
        System.out.println("inserting patient into kieSession..");

        kieSession.insert(patient);

        while(this.isMonitored()) {
            System.out.printf("Patient %s is being monitored..\n", patient.getPatient().getName());
            try {
                Thread.sleep(5000);
                kieSession.getAgenda().getAgendaGroup("monitoring").setFocus();
                kieSession.fireAllRules();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Patient %s released from monitoring.\n", patient.getPatient().getName());
    }

    public MonitoringPatient getPatient() {
        return patient;
    }

    public void setPatient(MonitoringPatient patient) {
        this.patient = patient;
    }

    public boolean isMonitored() {
        return isMonitored;
    }

    public void setMonitored(boolean monitored) {
        isMonitored = monitored;
    }
}
