package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

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
        ModelAndView modelAndView = new ModelAndView();
        Optional<UserDto> userInDb = userService.findUserByEmail(userDto.getEmail());

        if (!userInDb.equals(Optional.empty())){
            bindingResult.rejectValue("email", "error.user",
                    "Podany e-mail już istnieje!");
        }else {
            userService.saveUser(userDto);
            model.addAttribute("successMessage", "Zarejestrowano!");
            model.addAttribute("userDto", new UserDto());
        }
        return "registration";
    }
}
