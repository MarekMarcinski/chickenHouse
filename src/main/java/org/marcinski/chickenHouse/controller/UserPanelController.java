package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class UserPanelController {

    private UserService userService;
    private BCryptPasswordEncoder encoder;

    public UserPanelController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping("/user_panel")
    public String getUserPanel(Model model,
                               @ModelAttribute("message") String message){
        model.addAttribute("message", message);
        return "user_panel";
    }

    @PostMapping("/user_panel")
    public RedirectView changePassword(String oldPass,
                                       String newPass,
                                       String confirmPass,
                                       Principal principal,
                                       RedirectAttributes attributes){
        String message;
        if (newPass.length()<5 || confirmPass.length()<5){
            message = "Hasło musi miec przynajmniej 5 znaków";
            attributes.addAttribute("message", message);
            return new RedirectView("/user_panel");
        }
        if (!newPass.equals(confirmPass)){
            message = "Hasła muszą być takie same!";
            attributes.addAttribute("message", message);
            return new RedirectView("/user_panel");
        }
        String email = principal.getName();

        message = userService.resetPassword(email, oldPass ,newPass);
        attributes.addAttribute("message", message);
        return new RedirectView("/user_panel");
    }
}
