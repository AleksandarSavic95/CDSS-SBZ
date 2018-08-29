package ftn.sbz.cdssserver.model.dto;

import ftn.sbz.cdssserver.model.Doctor;

import javax.validation.constraints.NotEmpty;

public class CreateUserDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public CreateUserDto() {
    }

    public Doctor createDoctor() {
        return new Doctor(name, username, password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
