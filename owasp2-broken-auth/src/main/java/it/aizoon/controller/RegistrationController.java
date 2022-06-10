package it.aizoon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.aizoon.dto.UserDto;
import it.aizoon.event.UserRegistrationEvent;
import it.aizoon.model.ApplicationUser;
import it.aizoon.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/adduser")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result) {
        if(result.hasErrors()) {
            return "add-user";
        }

        ApplicationUser applicationUser = userService.createUser(userDto);
        eventPublisher.publishEvent(new UserRegistrationEvent(applicationUser));
        return "redirect:adduser?validate";
    }
}
