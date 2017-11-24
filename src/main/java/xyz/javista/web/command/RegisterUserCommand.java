package xyz.javista.web.command;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RegisterUserCommand {


    @Length(min = 1, max = 32, message = "The name must be between 1 and 50 characters")
    private String name;

    @NotNull
    @Length(min = 1, max = 255, message = "The email must be between 1 and 255 characters")
    private String email;

    @NotNull
    @Length(min = 1, max = 32, message = "The login must be between 1 and 32 characters")
    private String login;

    @NotNull
    @Length(min = 1, max = 255, message = "The password must be between 1 and 255 characters")
    private String password;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
