package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.javista.core.domain.User;
import xyz.javista.core.repository.UserRepository;
import xyz.javista.exception.UserException;
import xyz.javista.mapper.UserMapper;
import xyz.javista.web.command.UpdateProfileCommand;
import xyz.javista.web.dto.UserDTO;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO getProfileData() {
        User user = userRepository.findByLogin(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin());
        if (user == null) {
            throw new UserException(UserException.FailReason.USER_NOT_FOUND, "User not found!");
        }
        return userMapper.toDto(user);
    }

    public UserDTO updateProfile(UpdateProfileCommand profileCommand) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User existed = userRepository.findByLogin(((User) auth.getPrincipal()).getLogin());

        if (existed == null) {
            throw new UserException(UserException.FailReason.USER_NOT_FOUND, "User not found!");
        }

        existed.setName(profileCommand.getName());
        existed.setEmail(profileCommand.getEmail());

        User saved = userRepository.saveAndFlush(existed);

        return userMapper.toDto(saved);
    }

}
