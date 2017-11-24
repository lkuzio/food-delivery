package xyz.javista.web.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.javista.core.domain.User;
import xyz.javista.web.dto.UserDTO;
import xyz.javista.exception.UserRegistrationException;
import xyz.javista.mapper.UserMapper;
import xyz.javista.core.repository.UserRepository;

@Component
public class RegisterUserCommandHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO registerUser(RegisterUserCommand userDTO) throws UserRegistrationException {
        User existed = userRepository.findByLogin(userDTO.getLogin());
        if(existed!=null){
            throw new UserRegistrationException();
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User saved = userRepository.saveAndFlush(user);
        return userMapper.toDto(saved);
    }
}
