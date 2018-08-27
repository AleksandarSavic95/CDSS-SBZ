package fda.sbz.cdssserver.service.serviceImpl;

import fda.sbz.cdssserver.model.Role;
import fda.sbz.cdssserver.model.User;
import fda.sbz.cdssserver.repository.UserRepository;
import fda.sbz.cdssserver.security.SecurityUtils;
import fda.sbz.cdssserver.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            System.out.println("User already exists");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.DOCTOR);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }

    private User getLoggedUser() {
        String username = SecurityUtils.getUsernameOfLoggedUser();
        if (username == null) {
            return null;
        }
        return userRepository.findByUsername(username);
    }
}
