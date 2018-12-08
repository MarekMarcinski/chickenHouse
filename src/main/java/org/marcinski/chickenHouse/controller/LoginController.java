package org.marcinski.chickenHouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping({"/", "/login"})
    public String login(@RequestParam(required = false) String error, Model model){
        if (error != null) {
            if (error.equals("Bad credentials")) {
                model.addAttribute("message", "Błędny email lub hasło, spróbuj ponownie");
            } else if (error.equals("User is disabled")) {
                model.addAttribute("message", "Konto nieaktywne, sprawdź swojego maila");
            }
        }
        return "login";
    }
}
