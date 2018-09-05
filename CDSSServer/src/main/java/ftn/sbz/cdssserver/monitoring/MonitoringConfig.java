package ftn.sbz.cdssserver.monitoring;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class MonitoringConfig {

    private static final String KIE_SESSION_NAME = "MonitoringSession";

    @Bean(name = "monitoring")
    @Scope
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession(KIE_SESSION_NAME);
    }

    @Bean(name = "monitoringTaskExecutor")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }
}
