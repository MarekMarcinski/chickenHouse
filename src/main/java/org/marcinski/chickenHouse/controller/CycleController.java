package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.*;
import org.marcinski.chickenHouse.service.ChickenHouseService;
import org.marcinski.chickenHouse.service.CycleService;
import org.marcinski.chickenHouse.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
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
    public String createCycle(@PathVariable Long chickenHouseId,
                              @Valid CycleDto cycleDto,
                              BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            cycleService.createCycle(cycleDto, chickenHouseId);
        }
        return "redirect:/home/" + chickenHouseId;
    }

    @PutMapping("/{id}")
    public String updateCycle(@PathVariable Long id,
                              @Valid CycleDto cycleDto,
                              BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            cycleService.updateCycle(cycleDto, id);
        }

        return "redirect:/home/cycle/" + cycleDto.getId();
    }

    @PutMapping("complete/{id}")
    public String completeCycle(@PathVariable Long id){
        long chickenHouseId = cycleService.completeCycle(id);

        return "redirect:/home/" + chickenHouseId;
    }

    @GetMapping("/{id}")
    public String getCycle(@PathVariable Long id,
                           Model model,
                           Principal principal){
        DayDto dayDto = new DayDto();
        ForageDto forageDto = new ForageDto();
        CycleDto cycleDto;
        MedicineDto medicineDto = new MedicineDto();

        try {
            cycleDto = cycleService.getDtoById(id);
        }catch (EntityNotFoundException e){
            //TODO 404
            return "redirect/home";
        }

        if (!cycleService.isUserCycle(principal, cycleDto)){
            return "redirect:/home";
        }

        int actualNumberOfChicken = cycleDto.getNumberOfChickens();

        List<DayDto> dayDtos = dayService.getAllDaysByCycleIdSortedByDayNumber(id);
        Long chickenHouseId = cycleDto.getChickenHouseDto().getId();

        int actualDayNumber = 1;
        if (dayDtos.size() > 0){
            actualDayNumber = dayDtos.get(0).getDayNumber() + 1;
        }
        dayDto.setDayNumber(actualDayNumber);

        List<ForageDto> forages = new ArrayList<>();
        List<MedicineListDto> medicineListDtos = new ArrayList<>();

        for (DayDto dto : dayDtos) {
            int allDowns = dto.getNaturalDowns() + dto.getSelectionDowns();
            actualNumberOfChicken -= allDowns;
            if (dto.getForageDto() != null){
                forages.add(dto.getForageDto());
            }if (dto.getMedicineListDto() != null){
                medicineListDtos.add(dto.getMedicineListDto());
            }
        }

        model.addAttribute("chickenHouseId", chickenHouseId);
        model.addAttribute("cycle", cycleDto);
        model.addAttribute("dayDto", dayDto);
        model.addAttribute("actualNumberOfChicken", actualNumberOfChicken);
        model.addAttribute("days", dayDtos);
        model.addAttribute("forageDto", forageDto);
        model.addAttribute("medicineDto", medicineDto);
        model.addAttribute("forages", forages);
        model.addAttribute("medicineListDtos", medicineListDtos);

        return "cycle";
    }
}
