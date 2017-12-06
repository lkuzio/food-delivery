package xyz.javista.web.command;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UpdateProfileCommand {

    @NotNull
    @Length(min = 1, max = 32, message = "The name must be between 1 and 50 characters")
    private String name;

    @NotNull
    @Length(min = 1, max = 255, message = "The email must be between 1 and 255 characters")
    private String email;

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
}
