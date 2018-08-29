package ftn.sbz.cdssserver.controller;

import ftn.sbz.cdssserver.model.User;
import ftn.sbz.cdssserver.model.dto.CreateUserDto;
import ftn.sbz.cdssserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity createAdmin(@RequestBody @Valid CreateUserDto userDto) {
        final User created = userService.register(userDto.createDoctor());

        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping
    public ResponseEntity createDoctor(@RequestBody @Valid CreateUserDto userDto) {
        System.out.println("DOC\n\n");
        final User created = userService.register(userDto.createDoctor());

        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created.getId(), HttpStatus.CREATED);
    }
}
