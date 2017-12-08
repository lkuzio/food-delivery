package xyz.javista.web.dto;

import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String name;
    private String email;
    private String login;

    public UserDTO(){

    }

    public UserDTO(UUID id, String name, String email, String login) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
