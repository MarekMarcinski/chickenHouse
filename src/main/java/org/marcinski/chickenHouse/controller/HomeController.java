package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.ChickenHouseDto;
import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.ChickenHouseService;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
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

        String email = principal.getName();
        Optional<UserDto> userByEmail = userService.findUserByEmail(email);

        UserDto userDto;
        if (userByEmail.isPresent()){
            userDto = userByEmail.get();
            model.addAttribute("greeting", "Witaj " + userDto.getName());
        }

        List<ChickenHouseDto> chickenHouseDtos = chickenHouseService.findChickenHousesDtoByUserEmail(email);

        model.addAttribute("chickenHouseDtos", chickenHouseDtos);
        model.addAttribute("chickenHouseDto", chickenHouseDto);
        return "home";
    }
}