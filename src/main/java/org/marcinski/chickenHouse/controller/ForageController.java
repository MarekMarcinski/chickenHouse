package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.ForageDto;
import org.marcinski.chickenHouse.service.DayService;
import org.marcinski.chickenHouse.service.ForageService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("home/cycle/day/forage/")
public class ForageController {

    private DayService dayService;
    private ForageService forageService;

    public ForageController(DayService dayService, ForageService forageService) {
        this.dayService = dayService;
        this.forageService = forageService;
    }

    @PutMapping("/{dayId}")
    public String addForageToDay(@PathVariable Long dayId,
                                 @Valid ForageDto forageDto,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){

            return "redirect:/home";
        }
        long cycleId = dayService.addForageToDay(forageDto, dayId);

        if (cycleId == -1){
            //TODO 403
            return "redirect:/home";
        }

        return "redirect:/home/cycle/" + cycleId;
    }

    @DeleteMapping("/{forageDayId}")
    public String deleteForageFromDay(@PathVariable(name = "forageDayId") Long id){
        try {
            long cycleId = forageService.deleteForage(id);
            return "redirect:/home/cycle/" + cycleId;
        }catch (EntityNotFoundException e){
            //TODO 403
            return "redirect:/home";
        }
    }
}
