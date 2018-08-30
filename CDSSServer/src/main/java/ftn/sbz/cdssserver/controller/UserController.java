package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.User;
import ftn.sbz.cdssserver.model.dto.CreateUserDto;
import ftn.sbz.cdssserver.model.dto.LoginDto;
import ftn.sbz.cdssserver.security.TokenUtils;
import ftn.sbz.cdssserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private TokenUtils tokenUtils;


    public UserController(AuthenticationManager authenticationManager, UserService userService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDto user) {
        String username = user.getUsername();
        System.out.println("LOGIN TRY " + username + " pass: " + user.getPassword());

        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails details = userService.loadUserByUsername(username);
            return new ResponseEntity<>(tokenUtils.generateToken(details), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Login failed. Bad username or password.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity createDoctor(@RequestBody @Valid CreateUserDto userDto) {
        final User created = userService.register(userDto.createDoctor());

        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

}
