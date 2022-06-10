package it.aizoon.service;

import it.aizoon.dto.UserDto;
import it.aizoon.model.ApplicationUser;

public interface UserService {
    ApplicationUser createUser(UserDto userDto);
    ApplicationUser save(ApplicationUser applicationUser);
    ApplicationUser findByUsername(String username);
}
