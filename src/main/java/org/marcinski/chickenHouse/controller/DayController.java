package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.DayDto;
import org.marcinski.chickenHouse.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("home/cycle/day")
public class DayController {

    private DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @PostMapping("/{cycleId}")
    public String createDay(@PathVariable Long cycleId,
                            @Valid DayDto dayDto,
                            BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            dayService.createDay(dayDto, cycleId);
        }
        return "redirect:/home/cycle/" + cycleId;
    }

    @PutMapping("/{dayId}")
    public String editDay(@PathVariable Long dayId,
                          @Valid DayDto dayDto,
                          BindingResult bindingResult){
        long cycleId;
        if (!bindingResult.hasErrors()){
            cycleId = dayService.editDay(dayDto, dayId);
            return "redirect:/home/cycle/" + cycleId;
        }
        cycleId = dayDto.getCycleDto().getId();
        return "redirect:/home/cycle/" + cycleId;
    }
}
