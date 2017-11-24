package xyz.javista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import xyz.javista.core.domain.User;
import xyz.javista.dto.UserDTO;
import xyz.javista.mapper.UserMapper;
import xyz.javista.query.GetUserListQuery;
import xyz.javista.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public Page<UserDTO> getUsers(GetUserListQuery query){
        Page<User> result = userRepository.findAll(query);
        return result.map(x->userMapper.toDto(x));
    }
}
