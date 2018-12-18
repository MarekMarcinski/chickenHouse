package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.entity.Slaughter;
import org.marcinski.chickenHouse.mapper.SlaughterMapper;
import org.marcinski.chickenHouse.repository.SlaughterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SlaughterService {

    private SlaughterRepository slaughterRepository;
    private SlaughterMapper slaughterMapper;
    private CycleService cycleService;

    public SlaughterService(SlaughterRepository slaughterRepository, SlaughterMapper slaughterMapper, CycleService cycleService) {
        this.slaughterRepository = slaughterRepository;
        this.slaughterMapper = slaughterMapper;
        this.cycleService = cycleService;
    }

    public void createNewSlaughter(SlaughterDto slaughterDto, Long cycleId) {
        CycleDto cycleDto = null;
        try {
            cycleDto = cycleService.getDtoById(cycleId);
        }catch (EntityNotFoundException e){

        }
        slaughterDto.setCycleDto(cycleDto);

        Slaughter slaughter = slaughterMapper.mapTo(slaughterDto);
        slaughterRepository.save(slaughter);
    }
}
