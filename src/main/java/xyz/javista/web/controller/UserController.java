package xyz.javista.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.javista.web.dto.UserDTO;
import xyz.javista.core.query.GetUserListQuery;
import xyz.javista.service.UserService;


@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<UserDTO> getUsers(@RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                  @RequestParam(name = "limit", defaultValue = "0", required = false) int limit) {
        GetUserListQuery query = new GetUserListQuery(size, limit);
        return userService.getUsers(query);
    }

}
