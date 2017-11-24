package xyz.javista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.javista.command.RegisterUserCommand;
import xyz.javista.dto.UserDTO;
import xyz.javista.exception.UserRegistrationException;
import xyz.javista.handler.command.RegisterUserCommandHandler;
import xyz.javista.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value="registration")
public class RegisterController {

    @Autowired
    RegisterUserCommandHandler registerUserCommandHandler;

    @RequestMapping(method = RequestMethod.POST)
    public void register(@RequestBody @Valid RegisterUserCommand registerUserCommand) throws UserRegistrationException {
        registerUserCommandHandler.registerUser(registerUserCommand);
    }
}
