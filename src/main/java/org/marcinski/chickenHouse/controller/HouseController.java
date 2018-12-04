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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public ModelAndView house(@PathVariable Long id, Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        CycleDto cycleDto = new CycleDto();
        ChickenHouseDto chickenHouseDto = new ChickenHouseDto();
        LocalDate now = LocalDate.now();
        cycleDto.setStartDay(now);

        ChickenHouseDto chickenHouseById = chickenHouseService.getChickenHouseById(id);
            String userEmailFromHouse = chickenHouseById.getUserDto().getEmail();
            String userEmailFromPrincipal = principal.getName();

        if (userEmailFromHouse.equals(userEmailFromPrincipal)) {
            chickenHouseDto.setName(chickenHouseById.getName());
            chickenHouseDto.setAreaOfHouse(chickenHouseById.getAreaOfHouse());
            List<CycleDto> cycleDtos = cycleService.getAllByChickenHouseIdOrderedByStartDayDesc(id);

            modelAndView.addObject("house", chickenHouseById);
            modelAndView.addObject("date", now);
            modelAndView.addObject("cycleDto", cycleDto);
            modelAndView.addObject("cyclesDto", cycleDtos);
            modelAndView.addObject("actualChickenHouse", chickenHouseDto);
            modelAndView.setViewName("house");
        }
        modelAndView.addObject("chickenHouseDto", chickenHouseDto);

        return modelAndView;
    }

    @PostMapping("/new_house")
    public String addNewHouse(@Valid ChickenHouseDto chickenHouseDto,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    Model model){
        String email = principal.getName();

        Optional<UserDto> userByEmail = userService.findUserByEmail(email);
        if (bindingResult.hasErrors()){
            return "redirect:/home";
        }
        else {
            if (userByEmail.isPresent()) {
                chickenHouseDto.setUserDto(userByEmail.get());
                chickenHouseService.saveChickenHouse(chickenHouseDto);
                model.addAttribute("chickenHouseDto", new ChickenHouseDto());
            }
        }
        return "redirect:/home";
    }

    @PutMapping("/{id}")
    public String editHouse(@Valid ChickenHouseDto chickenHouseDto,
                                  @PathVariable Long id){
        ChickenHouseDto chickenHouseById = chickenHouseService.getChickenHouseById(id);

        chickenHouseById.setName(chickenHouseDto.getName());
        chickenHouseById.setAreaOfHouse(chickenHouseDto.getAreaOfHouse());
        chickenHouseService.saveChickenHouse(chickenHouseById);

        return "redirect:/home/" + chickenHouseDto.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteHouse(@PathVariable Long id){
        chickenHouseService.deleteHouse(id);
        return "redirect:/home/";
    }
}
