package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@Valid UserDto userDto, BindingResult bindingResult, Model model){
        if(!userDto.samePassword()){
            model.addAttribute("message", "Hasła nie pasują do siebie");
            return "registration";
        }

        if (!bindingResult.hasErrors()) {
            try {
                userService.findUserByEmail(userDto.getEmail());
                bindingResult.rejectValue("email", "error.user",
                        "Istnieje już użytkownik o podanym emailu!");
            } catch (EntityNotFoundException e) {
                userService.saveUser(userDto);
                model.addAttribute("message", "Użytkownik został zarejestrowany");
                model.addAttribute("userDto", new UserDto());
                return "registration";
            }
        }
        return "registration";
    }
}
