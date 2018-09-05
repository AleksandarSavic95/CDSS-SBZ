package ftn.sbz.cdssserver;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.monitoring.MonitoringPatient;
import ftn.sbz.cdssserver.model.monitoring.OxygenLevel;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.monitoring.MonitoringConfig;
import ftn.sbz.cdssserver.monitoring.MonitoringTask;
import ftn.sbz.cdssserver.service.PatientService;
import ftn.sbz.cdssserver.service.serviceImpl.MonitoringServiceImpl;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CdssserverApplication.class)//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringApplicationConfiguration(classes = CdssserverApplication.class)
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

    @BeforeClass
    public static void prepare() {
	    final Long patientId = 2L;

        final KieServices kieServices = KieServices.Factory.get();
        final KieContainer kieContainer = kieServices.newKieContainer(kieServices.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION));
        kieSession = kieContainer.newKieSession(KIE_SESSION_NAME);

        Sickness sickness = new Sickness("Chronic kidney disease");
        Patient patient = new Patient("p-2222", "Ljubivoje Rsumovic");

//        final TaskExecutor taskExecutor = new MonitoringConfig().threadPoolTaskExecutor();
//
//        final MonitoringServiceImpl service = new MonitoringServiceImpl(
//                kieSession, taskExecutor, template
//        );
//        patient.setId(patientId);
//        service.putPatientToIntensiveCare(patient, sickness);
//
//        // normal level of oxygen is between 80 and 100
//        monitoringTask = MonitoringServiceImpl.monitoringTasksMap.get(patientId);
//        monitoringTask.setPatientHandle(kieSession.insert(monitoringTask.getPatient()));
//        OxygenLevel oxygenLevel = new OxygenLevel(monitoringTask.getPatient(), 90, false);
//        monitoringTask.getPatient().setOxygenLevel(oxygenLevel);

        MonitoringPatient monitoringPatient = new MonitoringPatient();
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
		int fired = monitoringTask.changeOxygenLevel(60);
		assertEquals(1, fired);

		// all < 70
		fired = 0;
		for (int i = 10; i > 0; i--) {
			fired += monitoringTask.changeOxygenLevel(6 * i);
		}
		assertEquals(10, fired);

		// now the oxygen level has risen
		fired = monitoringTask.changeOxygenLevel(65);
		assertEquals(0, fired);

		// oxygen level has declined but in the past 15mins there was oxygen level rising
		fired = monitoringTask.changeOxygenLevel(60);
		assertEquals(0, fired);

		// 15 minutes later
		final SessionPseudoClock pseudoClock = kieSession.getSessionClock();
		pseudoClock.advanceTime(15, TimeUnit.MINUTES);

		fired = monitoringTask.changeOxygenLevel(50);
		assertEquals(1, fired);
	}
}
