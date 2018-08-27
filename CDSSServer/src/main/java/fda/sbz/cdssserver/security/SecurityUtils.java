package fda.sbz.cdssserver.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static String getUsernameOfLoggedUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        UserDetails securityUser = (UserDetails) authentication.getPrincipal();
        return securityUser.getUsername();
    }
}
