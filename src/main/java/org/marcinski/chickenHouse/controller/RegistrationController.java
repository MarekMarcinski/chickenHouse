package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping
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
                model.addAttribute("message", "Użytkownik został zarejestrowany, sprawdź email i aktywuj swoje konto!");
                model.addAttribute("userDto", new UserDto());
                return "registration";
            }
        }
        return "registration";
    }

    @GetMapping("/{uuid}")
    public String authorizeUser(@PathVariable String uuid, Model model){
        try{
            userService.authorizeUser(uuid);
            model.addAttribute("message", "Konto zostało aktywowane");
            return "login";
        }catch (EntityNotFoundException e){

            //TODO 404
            return "login";
        }
    }
}
