package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.service.CycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SlaughterController {

    private CycleService cycleService;

    public SlaughterController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @GetMapping("/home/cycle/slaughter/{cycleId}")
    public String getSlaughter(@PathVariable(name = "cycleId")Long id,
                               Model model){

        CycleDto cycleDtoById = cycleService.getDtoById(id);
        Long cycleId = cycleDtoById.getId();

        model.addAttribute("cycleId", cycleId);
        return "slaughter";
    }
}
