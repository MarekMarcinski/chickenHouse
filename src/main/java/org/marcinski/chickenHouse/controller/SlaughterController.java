package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.ChickenHouseDto;
import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.service.CycleService;
import org.marcinski.chickenHouse.service.SlaughterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home/cycle/slaughter")
public class SlaughterController {

    private CycleService cycleService;
    private SlaughterService slaughterService;

    public SlaughterController(CycleService cycleService,
                               SlaughterService slaughterService) {
        this.cycleService = cycleService;
        this.slaughterService = slaughterService;
    }

    @GetMapping("/{cycleId}")
    public String getSlaughter(@PathVariable(name = "cycleId") Long id,
                               Model model,
                               @ModelAttribute("message") String message,
                               Principal principal) {
        CycleDto cycleDtoById;
        try{
           cycleDtoById = cycleService.getDtoById(id);
        }catch (EntityNotFoundException e){
            //TODO 404
            return "redirect:/home";
        }

        if (!cycleService.isUserCycle(principal, cycleDtoById)){
            return "redirect:/home";
        }

        LocalDate now = LocalDate.now();
        SlaughterDto newSlaughter = new SlaughterDto();
        newSlaughter.setDateOfSlaughter(now);
        List<SlaughterDto> slaughterDtos = slaughterService.getAllSlaughterDtosByCycleId(id);

        if (message.length() != 0) {
            model.addAttribute("message", message);
        }

        model.addAttribute("cycleDtoById", cycleDtoById);
        model.addAttribute("newSlaughter", newSlaughter);
        model.addAttribute("now", now);
        model.addAttribute("slaughterDtos", slaughterDtos);
        return "slaughter";
    }

    @PostMapping("/new_slaughter/{cycleDtoId}")
    public RedirectView createNewSlaughter(@PathVariable(name = "cycleDtoId") Long cycleId,
                                           @Valid SlaughterDto slaughterDto,
                                           BindingResult bindingResult,
                                           RedirectAttributes attributes) {
        String message;
        if (!bindingResult.hasErrors()) {
            try {
                slaughterService.createNewSlaughter(slaughterDto, cycleId);
                message = "Ubój został zapisany.";
            } catch (EntityNotFoundException e) {
                message = "Coś poszło nie tak.";
            }
        } else {
            message = getErrorMsg(bindingResult);
        }
        attributes.addAttribute("message", message);
        return new RedirectView("/home/cycle/slaughter/" + cycleId);
    }

    @PutMapping("/{slaughterId}")
    public RedirectView editSlaughter(@PathVariable(name = "slaughterId") Long id,
                                      @Valid SlaughterDto slaughterDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes attributes) {
        SlaughterDto slaughterDtoById;
        CycleDto cycleDto = null;
        String message;
        if (!bindingResult.hasErrors()) {
            try {
                slaughterDtoById = slaughterService.getSlaughterDtoById(id);
                cycleDto = slaughterDtoById.getCycleDto();
                slaughterDto.setCycleDto(cycleDto);
                slaughterService.updateSlaughter(slaughterDto);
                message = "Ubój został zaktualizowany.";
            } catch (Exception e) {
                message = "Coś poszło nie tak";
            }
        }else {
            message = getErrorMsg(bindingResult);
        }
        attributes.addAttribute("message", message);
        return new RedirectView("/home/cycle/slaughter/" + cycleDto.getId());
    }

    private String getErrorMsg(BindingResult bindingResult) {
        String errorMessage = "";
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            errorMessage += error.getDefaultMessage() + "<br>";
        }
        return errorMessage;
    }
}
