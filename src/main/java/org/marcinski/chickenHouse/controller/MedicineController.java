package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.MedicineDto;
import org.marcinski.chickenHouse.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("home/cycle/day/medicine")
public class MedicineController {

    private DayService dayService;

    public MedicineController(DayService dayService) {
        this.dayService = dayService;
    }

    @PutMapping("/{dayId}")
    public String addMedicineToDay(@Valid MedicineDto medicineDto, @PathVariable Long dayId){
        long cycleId = dayService.addMedicineToDay(medicineDto, dayId);

        return "redirect:/home/cycle/" + cycleId;
    }
}
