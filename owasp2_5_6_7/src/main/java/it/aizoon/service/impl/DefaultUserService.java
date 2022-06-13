package it.aizoon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.aizoon.dto.UserDto;
import it.aizoon.model.ApplicationUser;
import it.aizoon.repository.UserRepository;
import it.aizoon.service.UserService;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApplicationUser createUser(UserDto userDto) {
       ApplicationUser applicationUser = new ApplicationUser();
       applicationUser.setFirstName(userDto.getFirstName());
       applicationUser.setLastName(userDto.getLastName());
       applicationUser.setEmail(userDto.getEmail());
       applicationUser.setUsername(userDto.getUsername());
       applicationUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
       applicationUser.setVerified(true); //avoid email

       return userRepository.save(applicationUser);
    }

    public ApplicationUser save(ApplicationUser applicationUser) {
        return userRepository.save(applicationUser);
    }

    public ApplicationUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
