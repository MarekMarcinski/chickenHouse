package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.SlaughterDto;
import org.marcinski.chickenHouse.entity.Slaughter;
import org.marcinski.chickenHouse.mapper.SlaughterMapper;
import org.marcinski.chickenHouse.repository.SlaughterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

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

    public void createNewSlaughter(SlaughterDto slaughterDto, Long cycleId) throws EntityNotFoundException {
        CycleDto cycleDto = cycleService.getDtoById(cycleId);

        slaughterDto.setCycleDto(cycleDto);

        Slaughter slaughter = slaughterMapper.mapTo(slaughterDto);
        slaughterRepository.save(slaughter);
    }

    public List<SlaughterDto> getAllSlaughterDtosByCycleId(Long id) {
        CycleDto cycleDto = cycleService.getDtoById(id);
        LocalDate startDay = cycleDto.getStartDay();

        List<Slaughter> allByCycleId = slaughterRepository.findAllByCycleId(id);
        List<SlaughterDto> slaughterDtos = allByCycleId
                .stream()
                .map(slaughter -> slaughterMapper.mapTo(slaughter))
                .collect(Collectors.toList());

        for (SlaughterDto slaughterDto : slaughterDtos) {
            int fatteningDay = setFatteningDay(slaughterDto, startDay);
            slaughterDto.setFatteningDay(fatteningDay);
        }
        return slaughterDtos;
    }

    public void updateSlaughter(SlaughterDto slaughterDto) {
        Slaughter slaughter = slaughterMapper.mapTo(slaughterDto);
        slaughterRepository.save(slaughter);
    }

    public SlaughterDto getSlaughterDtoById(Long id) {
        Slaughter slaughterById = slaughterRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return slaughterMapper.mapTo(slaughterById);
    }

    private int setFatteningDay(SlaughterDto slaughterDto, LocalDate startDay){
        LocalDate slaughterDate = slaughterDto.getDateOfSlaughter();
        return Period.between(startDay, slaughterDate).getDays();
    }
}
