package ftn.sbz.cdssserver;

import ftn.sbz.cdssserver.model.Patient;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class CdssserverApplicationTests {
	private static final String GROUP_ID = "ftn.sbz";
	private static final String ARTIFACT_ID = "drools-spring-kjar";
	private static final String VERSION = "0.0.1-SNAPSHOT";
	private static final String KIE_SESSION_NAME = "TestSession";

    @Autowired
    private static SimpMessagingTemplate template;

    private static MonitoringTask task;

    private static KieSession kieSession;

    private static SessionPseudoClock pseudoClock;

    static MonitoringPatient monitoringPatient;

    @BeforeClass
    public static void prepare() {
        // create a KieSession
        final KieServices kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION));
        kieSession = kieContainer.newKieSession(KIE_SESSION_NAME);

        kieSession.setGlobal("messagingTemplate", template); // !!!
        pseudoClock = kieSession.getSessionClock();

        // create a patient and put him on monitoring
        Patient patient = new Patient("p-2222", "Ljubivoje Rsumovic");
        monitoringPatient = new MonitoringPatient();
        monitoringPatient.setPatient(patient);
        // sickness is null for Oxygen and Heart tests!
        OxygenLevel oxygen = new OxygenLevel(monitoringPatient, 90, true);
        monitoringPatient.setOxygenLevel(oxygen);

        // create a task (assignment) for monitoring the patient
        task = new MonitoringTask();
        task.setKieSession(kieSession);
        task.setPatient(monitoringPatient);
        task.setPatientHandle(kieSession.insert(task.getPatient()));
    }

	@Test
	public void testOxygenProblems() {
        // make sure no other value affects the test
        pseudoClock.advanceTime(16, TimeUnit.MINUTES);

        // drop level below 70. No growth, since the initial value is 90
        int rulesFired = task.changeOxygenLevel(60);
		assertEquals(1, rulesFired);

		// all levels below 70
		rulesFired = 0;
		for (int i = 10; i > 0; i--) {
			rulesFired += task.changeOxygenLevel(6 * i);
		}
		assertEquals(10, rulesFired);

		// oxygen level is rising
		rulesFired = task.changeOxygenLevel(65);
		assertEquals(0, rulesFired);

		// oxygen level droped, but a recent (15m) rise is present
		rulesFired = task.changeOxygenLevel(60);
		assertEquals(0, rulesFired);

		// 15 minutes later
		pseudoClock.advanceTime(15, TimeUnit.MINUTES);

		rulesFired = task.changeOxygenLevel(50);
		assertEquals(1, rulesFired);
	}


	@Test
    public void testHeartProblems() {
        // 24 heartbeats in 10s
        int rulesFired = 0;
        for (int i = 0; i < 24; i++) {
            rulesFired += task.addHeartBeat();
        }
        assertEquals(0, rulesFired);

        // 25 heartbeats in 10s
        rulesFired = task.addHeartBeat();
        assertEquals(0, rulesFired);

        // > 25 heartbeats in 10s
        rulesFired = task.addHeartBeat();
        assertEquals(1, rulesFired);

        // 10 seconds later
        final SessionPseudoClock pseudoClock = kieSession.getSessionClock();
        pseudoClock.advanceTime(10, TimeUnit.SECONDS);

        // < 25 heart beats in 10s
        rulesFired = task.addHeartBeat();
        assertEquals(0, rulesFired);
    }

    @Test
    public void testUrgentDialysis() {
        int rulesFired;

        // patient suffers from Chronic kidney disease
        monitoringPatient.setSickness(new Sickness("Chronic kidney disease"));
        kieSession.update(task.getPatientHandle(), monitoringPatient);

        // Urination of over 100ml, but 12+h ago
        task.addUrination(200);
        rulesFired = task.addUrination(300);
        assertEquals(0, rulesFired);

        pseudoClock.advanceTime(12, TimeUnit.HOURS);

        // 10 (not more than 10) hear beats
        rulesFired = 0;
        for (int i = 0; i < 10; i++) {
            rulesFired += task.addHeartBeat();
        }
        assertEquals(0, rulesFired);

        // 10+ HeartBeats and no Urination
        rulesFired = task.addHeartBeat();
        assertEquals(1, rulesFired);

        // Still not-enough Urination
        rulesFired = task.addUrination(99);
        assertEquals(1, rulesFired);

        // 10 seconds later..
        pseudoClock.advanceTime(10, TimeUnit.SECONDS);

        // 102ml Urination occurred, but not enough HeartBeats
        task.addHeartBeat();
        task.addHeartBeat();
        rulesFired = task.addUrination(102);
        assertEquals(0, rulesFired);
    }
}
