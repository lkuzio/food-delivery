package xyz.javista.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.javista.service.ProfileService;
import xyz.javista.web.command.UpdateProfileCommand;
import xyz.javista.web.dto.UserDTO;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public UserDTO getProfile() {
        return profileService.getProfileData();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public UserDTO updateProfile(@RequestBody @Valid UpdateProfileCommand updateProfileCommand) {
        return profileService.updateProfile(updateProfileCommand);
    }
}
