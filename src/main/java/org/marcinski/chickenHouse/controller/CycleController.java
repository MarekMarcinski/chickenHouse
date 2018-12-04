package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.DayDto;
import org.marcinski.chickenHouse.dto.ForageDto;
import org.marcinski.chickenHouse.service.CycleService;
import org.marcinski.chickenHouse.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home/cycle")
public class CycleController {

    private CycleService cycleService;
    private DayService dayService;

    public CycleController(CycleService cycleService, DayService dayService) {
        this.cycleService = cycleService;
        this.dayService = dayService;
    }

    @PostMapping("/{chickenHouseId}")
    public String  createCycle(@Valid CycleDto cycleDto, @PathVariable Long chickenHouseId){
        cycleService.createCycle(cycleDto, chickenHouseId);
        //TODO validacja
        return "redirect:/home/" + chickenHouseId;
    }

    @PutMapping("/{id}")
    public String updateCycle(@Valid CycleDto cycleDto, @PathVariable Long id){
        cycleService.updateCycle(cycleDto, id);

        return "redirect:/home/cycle/" + cycleDto.getId();
    }

    @PutMapping("complete/{id}")
    public String updateCycle(@PathVariable Long id){
        long chickenHouseId = cycleService.completeCycle(id);

        return "redirect:/home/" + chickenHouseId;
    }

    @GetMapping("/{id}")
    public String getCycle(@PathVariable Long id, Model model){
        DayDto dayDto = new DayDto();
        ForageDto forageDto = new ForageDto();
        CycleDto cycleDto = cycleService.getDtoById(id);

        List<DayDto> dayDtos = dayService.getAllDaysByCycleIdSortedByDayNumber(id);
        int actualDayNumber = 1;
        if (dayDtos.size() > 0){
            actualDayNumber = dayDtos.get(0).getDayNumber() + 1;
        }
        dayDto.setDayNumber(actualDayNumber);

        int actualNumberOfChicken = cycleDto.getNumberOfChickens();
        List<ForageDto> forages = new ArrayList<>();
        for (DayDto dto : dayDtos) {
            int allDowns = dto.getNaturalDowns() + dto.getSelectionDowns();
            actualNumberOfChicken -= allDowns;
            if (dto.getForageDto() != null){
                forages.add(dto.getForageDto());
            }
        }
        model.addAttribute("cycle", cycleDto);
        model.addAttribute("dayDto", dayDto);
        model.addAttribute("actualNumberOfChicken", actualNumberOfChicken);
        model.addAttribute("days", dayDtos);
        model.addAttribute("forageDto", forageDto);
        model.addAttribute("forages", forages);

        return "cycle";
    }
}
