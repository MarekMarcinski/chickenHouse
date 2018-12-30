package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.DayDto;
import org.marcinski.chickenHouse.dto.MedicineDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.service.CycleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/home/cycle")
public class SummaryController {

    private CycleService cycleService;

    public SummaryController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @GetMapping("/{cycleId}/summary")
    public String getSummary(@PathVariable Long cycleId,
                             Model model,
                             Principal principal){
        CycleDto cycleDto;
        try {
            cycleDto = cycleService.getDtoById(cycleId);
        }catch (EntityNotFoundException e){

            return "404";
        }

        if (!cycleService.isUserCycle(principal, cycleDto)){
            return "404";
        }

        Set<DayDto> daysDto = cycleDto.getDaysDto();

        double foragePrice = 0.0;
        double medicinePrice = 0.0;
        for (DayDto dayDto : daysDto) {
            if(dayDto.getForageDto() != null){
                foragePrice += dayDto.getForageDto().getPrice();
            }
            if (dayDto.getMedicineListDto() != null){
                Set<MedicineDto> medicineDtos = dayDto.getMedicineListDto().getMedicineDtos();
                for (MedicineDto medicineDto : medicineDtos) {
                    medicinePrice += medicineDto.getPrice();
                }

            }
        }

        double chickenPrice = cycleDto.getNumberOfChickens() * cycleDto.getPrice();
        double totalCost = chickenPrice + foragePrice + medicinePrice;

        double slaughterPrice = 0.0;
        double totalWeight = 0.0;
        Set<SlaughterDto> slaugtherDtos = cycleDto.getSlaughterDtos();
        for (SlaughterDto slaugtherDto : slaugtherDtos) {
            slaughterPrice += slaugtherDto.getSellingPrice() * slaugtherDto.getWeightOfChickens();
            totalWeight += slaugtherDto.getWeightOfChickens();
        }

        double weightGain = totalWeight / cycleDto.getChickenHouseDto().getAreaOfHouse();

        model.addAttribute("cycleDto", cycleDto);
        model.addAttribute("daysDto", daysDto);
        model.addAttribute("chickenPrice", chickenPrice);
        model.addAttribute("foragePrice", foragePrice);
        model.addAttribute("medicinePrice", medicinePrice);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("slaughterPrice", slaughterPrice);
        model.addAttribute("weightGain", weightGain);

        return "summary";
    }
}
