package ftn.sbz.cdssserver;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.monitoring.HeartBeat;
import ftn.sbz.cdssserver.model.monitoring.MonitoringPatient;
import ftn.sbz.cdssserver.model.monitoring.OxygenLevel;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.monitoring.MonitoringTask;
import org.drools.core.time.SessionPseudoClock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebAppConfiguration
//@SpringBootTest(classes = CdssserverApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CdssserverApplicationTests {
	private static final String GROUP_ID = "ftn.sbz";
	private static final String ARTIFACT_ID = "drools-spring-kjar";
	private static final String VERSION = "0.0.1-SNAPSHOT";
	private static final String KIE_SESSION_NAME = "TestSession";

    @Autowired
    private static SimpMessagingTemplate template;

    private static MonitoringTask monitoringTask;

    private static KieSession kieSession;

    static MonitoringPatient monitoringPatient;

    @BeforeClass
    public static void prepare() {
        final KieServices kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION));
        kieSession = kieContainer.newKieSession(KIE_SESSION_NAME);
        kieSession.setGlobal("messagingTemplate", template); // !!!

        Sickness sickness = new Sickness("Chronic kidney disease");
        Patient patient = new Patient("p-2222", "Ljubivoje Rsumovic");

        monitoringPatient = new MonitoringPatient();
        monitoringPatient.setPatient(patient);
        monitoringPatient.setSickness(sickness);
        OxygenLevel oxygen = new OxygenLevel(monitoringPatient, 90, false);
        monitoringPatient.setOxygenLevel(oxygen);

        MonitoringTask task = new MonitoringTask();
        task.setKieSession(kieSession);
        task.setPatient(monitoringPatient);
        task.setPatientHandle(kieSession.insert(task.getPatient()));
        monitoringTask = task; // !!! // TODO: COMPLETELY REPLACE `monitoringTask` with `task`!
    }

	@Test
	public void testOxygenProblems() {
		// < 70
		int rulesFired = monitoringTask.changeOxygenLevel(60);
		assertEquals(1, rulesFired);

		// all < 70
		rulesFired = 0;
		for (int i = 10; i > 0; i--) {
			rulesFired += monitoringTask.changeOxygenLevel(6 * i);
		}
		assertEquals(10, rulesFired);

		// now the oxygen level has risen
		rulesFired = monitoringTask.changeOxygenLevel(65);
		assertEquals(0, rulesFired);

		// oxygen level has declined but in the past 15mins there was oxygen level rising
		rulesFired = monitoringTask.changeOxygenLevel(60);
		assertEquals(0, rulesFired);

		// 15 minutes later
		final SessionPseudoClock pseudoClock = kieSession.getSessionClock();
		pseudoClock.advanceTime(15, TimeUnit.MINUTES);

		rulesFired = monitoringTask.changeOxygenLevel(50);
		assertEquals(1, rulesFired);
	}


	@Test
    public void testHeartProblems() {
        // 24 heartbeats in 10s
        int rulesFired = 0;
        for (int i = 0; i < 24; i++) {
//            kieSession.insert(new HeartBeat(monitoringPatient));
//            kieSession.getAgenda().getAgendaGroup("monitoring").setFocus();
//            rulesFired += kieSession.fireAllRules();
            rulesFired += monitoringTask.addHeartBeat();
        }
        assertEquals(0, rulesFired);

        // 25 heartbeats in 10s
//        kieSession.insert(new HeartBeat(monitoringPatient));
//        kieSession.getAgenda().getAgendaGroup("monitoring").setFocus();
//        rulesFired = kieSession.fireAllRules();
        rulesFired = monitoringTask.addHeartBeat();
        assertEquals(0, rulesFired);

        // > 25 heartbeats in 10s
//        kieSession.insert(new HeartBeat(monitoringPatient));
//        kieSession.getAgenda().getAgendaGroup("monitoring").setFocus();
//        rulesFired = kieSession.fireAllRules();
        rulesFired = monitoringTask.addHeartBeat();
        assertEquals(1, rulesFired);

        // 10 seconds later
        final SessionPseudoClock pseudoClock = kieSession.getSessionClock();
        pseudoClock.advanceTime(10, TimeUnit.SECONDS);

        // < 25 heart beats in 10s
//        kieSession.insert(new HeartBeat(monitoringPatient));
//        kieSession.getAgenda().getAgendaGroup("monitoring").setFocus();
//        rulesFired = kieSession.fireAllRules();
        rulesFired = monitoringTask.addHeartBeat();
        assertEquals(0, rulesFired);
    }
}
