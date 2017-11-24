package xyz.javista.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.javista.web.command.RegisterUserCommand;
import xyz.javista.web.dto.UserDTO;
import xyz.javista.exception.UserRegistrationException;
import xyz.javista.web.command.RegisterUserCommandHandler;

import javax.validation.Valid;

@RestController
@RequestMapping(value="registration")
public class RegistrationController {

    @Autowired
    RegisterUserCommandHandler registerUserCommandHandler;

    @RequestMapping(method = RequestMethod.POST)
    public UserDTO register(@RequestBody @Valid RegisterUserCommand registerUserCommand) throws UserRegistrationException {
        return registerUserCommandHandler.registerUser(registerUserCommand);
    }
}
