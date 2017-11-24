package xyz.javista.mapper;

import org.mapstruct.Mapper;
import xyz.javista.command.RegisterUserCommand;
import xyz.javista.core.domain.User;
import xyz.javista.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    User toEntity(RegisterUserCommand userDTO);

    UserDTO toDto(User user);
}
