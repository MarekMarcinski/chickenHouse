package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.mapper.SlaughterMapper;
import org.marcinski.chickenHouse.service.CycleService;
import org.marcinski.chickenHouse.service.SlaughterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/home/cycle/slaughter")
public class SlaughterController {

    private CycleService cycleService;
    private SlaughterMapper slaughterMapper;
    private SlaughterService slaughterService;

    public SlaughterController(CycleService cycleService,
                               SlaughterMapper slaughterMapper,
                               SlaughterService slaughterService) {
        this.cycleService = cycleService;
        this.slaughterMapper = slaughterMapper;
        this.slaughterService = slaughterService;
    }

    @GetMapping("/{cycleId}")
    public String getSlaughter(@PathVariable(name = "cycleId")Long id,
                               Model model){

        CycleDto cycleDtoById = cycleService.getDtoById(id);
        LocalDate now = LocalDate.now();
        SlaughterDto newSlaughter = new SlaughterDto();
        newSlaughter.setDateOfSlaughter(now);

        model.addAttribute("cycleDtoById", cycleDtoById);
        model.addAttribute("newSlaughter", newSlaughter);
        model.addAttribute("now", now);
        return "slaughter";
    }

    @PostMapping("/new_slaughter/{cycleDtoId}")
    public String createNewSlaughter(@PathVariable(name = "cycleDtoId") Long cycleId,
                                     @Valid SlaughterDto slaughterDto,
                                     BindingResult bindingResult){
        slaughterService.createNewSlaughter(slaughterDto, cycleId);

        return "redirect:/home/cycle/" + cycleId;
    }
}
