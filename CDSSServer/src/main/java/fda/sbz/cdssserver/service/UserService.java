package fda.sbz.cdssserver.service;

import fda.sbz.cdssserver.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;


public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    User register(User user);
}
