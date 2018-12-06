package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("home/cycle/day/forage/")
public class ForageDto {

    private DayService dayService;

    public ForageDto(DayService dayService) {
        this.dayService = dayService;
    }

    @PutMapping("/{dayId}")
    public String addForageToDay(@Valid org.marcinski.chickenHouse.dto.ForageDto forageDto, @PathVariable Long dayId){

        long cycleId = dayService.addForageToDay(forageDto, dayId);

        return "redirect:/home/cycle/" + cycleId;
    }
}
