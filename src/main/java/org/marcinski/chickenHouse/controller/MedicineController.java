package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.MedicineDto;
import org.marcinski.chickenHouse.service.DayService;
import org.marcinski.chickenHouse.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("home/cycle/day/medicine")
public class MedicineController {

    private DayService dayService;
    private MedicineService medicineService;

    public MedicineController(DayService dayService,
                              MedicineService medicineService) {
        this.dayService = dayService;
        this.medicineService = medicineService;
    }

    @PutMapping("/{dayId}")
    public String addMedicineToDay(@PathVariable Long dayId,
                                   @Valid MedicineDto medicineDto,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "redirect:/home";
        }

        long cycleId = dayService.addMedicineToDay(medicineDto, dayId);

        return "redirect:/home/cycle/" + cycleId;
    }

    @DeleteMapping("/{medicineId}")
    public String deleteMedicine(@PathVariable(name = "medicineId") Long id) {
        try {
            long cycleId = medicineService.deleteMedicine(id);
            return "redirect:/home/cycle/" + cycleId;
        }catch (EntityNotFoundException e){
            //TODO 403
            return "redirect:/home";
        }
    }
}
