package ftn.sbz.cdssserver.service;

import ftn.sbz.cdssserver.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    User register(User user);
}
