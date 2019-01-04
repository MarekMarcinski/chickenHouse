package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;

@Controller
public class ReminderController {

    private UserService userService;

    public ReminderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reminder")
    public String getReminder(Model model,
                              @ModelAttribute("message")String message){
        model.addAttribute("message", message);
        return "reminder";
    }

    @PostMapping("/reminder")
    public RedirectView resetPassword(String email,
                                      RedirectAttributes attributes){
        String message;
        try {
            userService.resetPassword(email);
            message = "Nowe hasło zostało wysłane na podany adres email.";
        }catch (EntityNotFoundException e){
            message = "Nie znaleziono użytkownika o podanym emailu.";
        }
        attributes.addAttribute("message", message);

        return new RedirectView("/reminder");
    }
}
