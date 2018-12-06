package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.ChickenHouseDto;
import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.ChickenHouseService;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private UserService userService;
    private ChickenHouseService chickenHouseService;

    public HomeController(UserService userService, ChickenHouseService chickenHouseService) {
        this.userService = userService;
        this.chickenHouseService = chickenHouseService;
    }

    @GetMapping("/home")
    public String home(Principal principal, Model model){
        ChickenHouseDto chickenHouseDto = new ChickenHouseDto();

        List<ChickenHouseDto> chickenHouseDtos;
        try{
            String email = principal.getName();
            UserDto userByEmail = userService.findUserByEmail(email);
            model.addAttribute("greeting", "Cześć " + userByEmail.getName());
            chickenHouseDtos = chickenHouseService.findChickenHousesDtoByUserEmail(email);

        }catch (EntityNotFoundException e){
            return "login";
        }

        model.addAttribute("chickenHouseDtos", chickenHouseDtos);
        model.addAttribute("chickenHouseDto", chickenHouseDto);
        return "home";
    }
}