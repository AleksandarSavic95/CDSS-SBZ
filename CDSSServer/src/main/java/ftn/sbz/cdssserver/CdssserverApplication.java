package ftn.sbz.cdssserver;

import ftn.sbz.cdssserver.security.SecurityUtils;
import ftn.sbz.cdssserver.service.KieSessionService;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@SpringBootApplication
public class CdssserverApplication {
    private static final String GROUP_ID = "ftn.sbz";
    private static final String ARTIFACT_ID = "drools-spring-kjar";
    private static final String VERSION = "0.0.1-SNAPSHOT";
    private static final String KIE_SESSION_NAME = "UserSession";

	public static void main(String[] args) {
		SpringApplication.run(CdssserverApplication.class, args);
	}

	@Bean
	// @ApplicationScoped
	public KieContainer kieContainer() {
		final KieServices kieServices = KieServices.Factory.get();

		final KieContainer kieContainer = kieServices.newKieContainer(kieServices.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION));
		final KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
		kieScanner.start(10000);

		return kieContainer;
	}

	@Bean
	@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
	public KieSession kieSession(KieSessionService kieSessionService) {
		return kieSessionService.getSession(SecurityUtils.getUsernameOfLoggedUser());
	}
}
