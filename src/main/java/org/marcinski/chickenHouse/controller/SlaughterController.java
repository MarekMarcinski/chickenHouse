package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.service.CycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/home/cycle/slaughter")
public class SlaughterController {

    private CycleService cycleService;

    public SlaughterController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @GetMapping("/{cycleId}")
    public String getSlaughter(@PathVariable(name = "cycleId")Long id,
                               Model model){

        CycleDto cycleDtoById = cycleService.getDtoById(id);
        SlaughterDto newSlaughter = new SlaughterDto();
        LocalDate now = LocalDate.now();

        model.addAttribute("cycleDtoById", cycleDtoById);
        model.addAttribute("newSlaughter", newSlaughter);
        model.addAttribute("now", now);
        return "slaughter";
    }
}
