package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.dto.DayDto;
import org.marcinski.chickenHouse.dto.ForageDto;
import org.marcinski.chickenHouse.entity.Day;
import org.marcinski.chickenHouse.mapper.DayMapper;
import org.marcinski.chickenHouse.repository.DayRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DayService {

    private DayRepository dayRepository;
    private CycleService cycleService;
    private ForageService forageService;
    private DayMapper dayMapper;

    public DayService(DayRepository dayRepository, CycleService cycleService, ForageService forageService, DayMapper dayMapper) {
        this.dayRepository = dayRepository;
        this.cycleService = cycleService;
        this.forageService = forageService;
        this.dayMapper = dayMapper;
    }

    public void createDay(DayDto dayDto, Long cycleId) {
        CycleDto cycleDto = cycleService.getDtoById(cycleId);
        dayDto.setCycleDto(cycleDto);

        Day day = dayMapper.mapTo(dayDto);
        dayRepository.save(day);

    }

    public List<DayDto> getAllDaysByCycleIdSortedByDayNumber(Long cycleId){
        CycleDto cycleDto = cycleService.getDtoById(cycleId);

        List<DayDto> dayDtos = new ArrayList<>(cycleDto.getDaysDto());
        dayDtos.sort(Comparator.comparingInt(DayDto::getDayNumber).reversed());

        return dayDtos;
    }

    public long editDay(DayDto dayDto, Long dayId) {
        long cycleId;
            DayDto toEdit = getDayDtoByCycleId(dayId);
            toEdit.setNaturalDowns(dayDto.getNaturalDowns());
            toEdit.setSelectionDowns(dayDto.getSelectionDowns());
            toEdit.setWaterCounter(dayDto.getWaterCounter());
            toEdit.setAverageWeight(dayDto.getAverageWeight());
            toEdit.setComments(dayDto.getComments());
            cycleId = toEdit.getCycleDto().getId();
            dayRepository.save(dayMapper.mapTo(toEdit));
        return cycleId;
    }

    public long addForageToDay(ForageDto forageDto, Long dayId) {
        long cycleId;
        DayDto dayToEdit = getDayDtoByCycleId(dayId);
        ForageDto forageToAdd = forageService.createForage(forageDto);
        dayToEdit.setForageDto(forageToAdd);

        cycleId = dayToEdit.getCycleDto().getId();
        dayRepository.save(dayMapper.mapTo(dayToEdit));

        return cycleId;
    }

    private DayDto getDayDtoByCycleId(Long dayId){
        return dayRepository.findById(dayId)
                .map(dayMapper::mapTo)
                .orElseThrow(EntityNotFoundException::new);
    }
}
