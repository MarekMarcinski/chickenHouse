package org.marcinski.chickenHouse.controller;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.mapper.SlaughterMapper;
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
import java.time.LocalDate;
import java.util.List;

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
                               Model model,
                               @ModelAttribute("message") String message){

        CycleDto cycleDtoById = cycleService.getDtoById(id);
        LocalDate now = LocalDate.now();
        SlaughterDto newSlaughter = new SlaughterDto();
        newSlaughter.setDateOfSlaughter(now);

        if (message.length() != 0) {
            model.addAttribute("message", message);
        }

        model.addAttribute("cycleDtoById", cycleDtoById);
        model.addAttribute("newSlaughter", newSlaughter);
        model.addAttribute("now", now);
        return "slaughter";
    }

    @PostMapping("/new_slaughter/{cycleDtoId}")
    public RedirectView createNewSlaughter(@PathVariable(name = "cycleDtoId") Long cycleId,
                                           @Valid SlaughterDto slaughterDto,
                                           BindingResult bindingResult,
                                           RedirectAttributes attributes){
        String message;
        if(!bindingResult.hasErrors()) {
            try {
                slaughterService.createNewSlaughter(slaughterDto, cycleId);
                message = "Ubój został zapisany.";
            }catch (EntityNotFoundException e){
                message = "Coś poszło nie tak.";
            }
        }else {
            message = getErrorMsg(bindingResult);
        }
        attributes.addAttribute("message", message);
        return new RedirectView("/home/cycle/slaughter/" + cycleId);
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
