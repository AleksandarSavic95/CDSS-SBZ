package ftn.sbz.cdssserver.service.serviceImpl;

import ftn.sbz.cdssserver.model.Patient;
import ftn.sbz.cdssserver.model.monitoring.MonitoringPatient;
import ftn.sbz.cdssserver.model.sickness.Sickness;
import ftn.sbz.cdssserver.monitoring.MonitoringTask;
import ftn.sbz.cdssserver.service.MonitoringService;
import ftn.sbz.cdssserver.service.NotificationService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final KieSession kieSession;

    private final SimpMessagingTemplate template;

    private final TaskExecutor taskExecutor;

    private final NotificationService notificationService;

    public static HashMap<Long, MonitoringTask> monitoringTasksMap = new HashMap<>();

    @Autowired
    public MonitoringServiceImpl(@Qualifier("monitoring") KieSession kieSession, @Qualifier("monitoringTaskExecutor") TaskExecutor taskExecutor, SimpMessagingTemplate template, NotificationService notificationService) {
        this.kieSession = kieSession;
        this.taskExecutor = taskExecutor;
        this.template = template;
        this.notificationService = notificationService;
        kieSession.setGlobal("monitoringService", this);
    }

    @Override
    public Collection<MonitoringPatient> getAllOnIntensiveCare() {
        return monitoringTasksMap.values().stream().map(MonitoringTask::getPatient).collect(Collectors.toList());
    }

    @Override
    public boolean putPatientToIntensiveCare(Patient patient, Sickness sickness) {
        return startMonitoring(patient, sickness);
    }

    @Override
    public boolean releasePatientFromIntensiveCare(long patientId) {
        MonitoringTask task = monitoringTasksMap.remove(patientId);
        if (task == null)
            return false;

        task.setMonitored(false); // !
        return true;
    }

    // called from rules!
    @Override
    public void sendMessage(String message) {
        notificationService.sendWarning(message);
    }

    private boolean startMonitoring(Patient patient, Sickness sickness) {
        MonitoringTask task = new MonitoringTask();
        task.setPatient(new MonitoringPatient(patient, sickness));
        task.setKieSession(kieSession);
        monitoringTasksMap.put(patient.getId(), task);

        try {
            taskExecutor.execute(task);
        }
        catch (RejectedExecutionException rex) {
            rex.printStackTrace();
            return false;
        }
        return true;
    }
}
