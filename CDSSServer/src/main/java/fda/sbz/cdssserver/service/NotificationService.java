package fda.sbz.cdssserver.service;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    void sendWarning(String message);

    void sendInfo(String message);

    void sendMessage(String message, String level);
}
