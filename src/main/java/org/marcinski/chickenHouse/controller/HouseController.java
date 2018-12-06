package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.ChickenHouseDto;
import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.service.ChickenHouseService;
import org.marcinski.chickenHouse.service.CycleService;
import org.marcinski.chickenHouse.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HouseController {

    private ChickenHouseService chickenHouseService;
    private CycleService cycleService;
    private UserService userService;

    public HouseController(ChickenHouseService chickenHouseService, CycleService cycleService,
                           UserService userService) {
        this.chickenHouseService = chickenHouseService;
        this.cycleService = cycleService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String house(@PathVariable Long id, Principal principal, Model model){
        CycleDto cycleDto = new CycleDto();
        ChickenHouseDto chickenHouseDto = new ChickenHouseDto();
        LocalDate now = LocalDate.now();
        cycleDto.setStartDay(now);

        ChickenHouseDto chickenHouseById;
        try{
            chickenHouseById = chickenHouseService.getChickenHouseById(id);
        }catch (EntityNotFoundException e){
            return "redirect:/home";
        }

        String userEmailFromHouse = chickenHouseById.getUserDto().getEmail();
        String userEmailFromPrincipal = principal.getName();

        if (!userEmailFromHouse.equals(userEmailFromPrincipal)){
            return "redirect:/home";
        }

        chickenHouseDto.setName(chickenHouseById.getName());
        chickenHouseDto.setAreaOfHouse(chickenHouseById.getAreaOfHouse());
        List<CycleDto> cycleDtos = cycleService.getAllByChickenHouseIdOrderedByStartDayDesc(id);

        model.addAttribute("house", chickenHouseById);
        model.addAttribute("date", now);
        model.addAttribute("cycleDto", cycleDto);
        model.addAttribute("cyclesDto", cycleDtos);
        model.addAttribute("actualChickenHouse", chickenHouseDto);
        model.addAttribute("chickenHouseDto", chickenHouseDto);

        return "house";
    }

    @PostMapping("/new_house")
    public String addNewHouse(@Valid ChickenHouseDto chickenHouseDto,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    Model model){
        String email = principal.getName();

        if (bindingResult.hasErrors()){
            return "redirect:/home";
        }
        try {
            UserDto userByEmail = userService.findUserByEmail(email);
            chickenHouseDto.setUserDto(userByEmail);
            chickenHouseService.saveChickenHouse(chickenHouseDto);
            model.addAttribute("chickenHouseDto", new ChickenHouseDto());
            return "redirect:/home";
        }catch (EntityNotFoundException e){

            return "redirect:/home";
        }
    }

    @PutMapping("/{id}")
    public String editHouse(@Valid ChickenHouseDto chickenHouseDto,
                            @PathVariable Long id,
                            BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            try {
                ChickenHouseDto chickenHouseById = chickenHouseService.getChickenHouseById(id);
                chickenHouseById.setName(chickenHouseDto.getName());
                chickenHouseById.setAreaOfHouse(chickenHouseDto.getAreaOfHouse());
                chickenHouseService.saveChickenHouse(chickenHouseById);
            }catch (EntityNotFoundException e){
                return "redirect/home";
            }
        }
        return "redirect:/home/" + chickenHouseDto.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteHouse(@PathVariable Long id){
        chickenHouseService.deleteHouse(id);
        return "redirect:/home/";
    }
}
