package ftn.sbz.cdssserver.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service // A
public class KieSessionService {
    private static HashMap<String, KieSession> sessionMap;
    private static final String KIE_SESSION_NAME = "UserSession";

    static {
        sessionMap = new HashMap<>(); // TODO: move to constructor?
    }

    public KieSession getSession(String username) {
        return sessionMap.get(username);
    }

    public void createSession(String username, KieContainer kieContainer) {
        // destroy old session
        if (sessionMap.containsKey(username))
            sessionMap.get(username).destroy();

        KieSession kieSession = kieContainer.newKieSession(KIE_SESSION_NAME);
        sessionMap.put(username, kieSession);

        System.out.println("Created sesId: " + kieSession.getIdentifier() + " for user: " + username);
    }
}
