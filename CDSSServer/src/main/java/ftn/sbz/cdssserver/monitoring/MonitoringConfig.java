package ftn.sbz.cdssserver.monitoring;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class MonitoringConfig {

    private static final String KIE_SESSION_NAME = "MonitoringSession";

    @Bean(name = "monitoring")
    @Scope
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession(KIE_SESSION_NAME);
    }
}
