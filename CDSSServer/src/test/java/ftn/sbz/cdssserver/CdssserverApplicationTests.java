package ftn.sbz.cdssserver;

import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CdssserverApplicationTests {
	private static final String GROUP_ID = "ftn.sbz";
	private static final String ARTIFACT_ID = "drools-spring-kjar";
	private static final String VERSION = "0.0.1-SNAPSHOT";
	private static final String KIE_SESSION_NAME = "TestSession";

	private KieSession createKieSession() {
		final KieServices kieServices = KieServices.Factory.get();
		final KieContainer kieContainer = kieServices.newKieContainer(kieServices.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION));

		return kieContainer.newKieSession(KIE_SESSION_NAME);
	}

}
