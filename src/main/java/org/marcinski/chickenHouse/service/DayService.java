package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.*;
import org.marcinski.chickenHouse.entity.Day;
import org.marcinski.chickenHouse.mapper.DayMapper;
import org.marcinski.chickenHouse.repository.DayRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

@Service
public class DayService {

    private DayRepository dayRepository;
    private CycleService cycleService;
    private ForageService forageService;
    private MedicineListService medicineListService;
    private MedicineService medicineService;
    private DayMapper dayMapper;

    public DayService(DayRepository dayRepository,
                      CycleService cycleService,
                      ForageService forageService,
                      DayMapper dayMapper,
                      MedicineListService medicineListService,
                      MedicineService medicineService) {
        this.dayRepository = dayRepository;
        this.cycleService = cycleService;
        this.forageService = forageService;
        this.dayMapper = dayMapper;
        this.medicineListService = medicineListService;
        this.medicineService = medicineService;
    }

    public void createDay(DayDto dayDto, Long cycleId) {
        CycleDto cycleDto = cycleService.getDtoById(cycleId);
        dayDto.setCycleDto(cycleDto);

        Day day = dayMapper.mapTo(dayDto);
        dayRepository.save(day);

    }

    public List<DayDto> getAllDaysByCycleIdSortedByDayNumber(Long cycleId){
        List<DayDto> dayDtos;
        try{
            CycleDto cycleDto = cycleService.getDtoById(cycleId);
            dayDtos = new ArrayList<>(cycleDto.getDaysDto());
            dayDtos.sort(Comparator.comparingInt(DayDto::getDayNumber).reversed());
        }catch (EntityNotFoundException e){
            dayDtos = Collections.emptyList();
        }
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
        DayDto dayToEdit;
        try {
            dayToEdit = getDayDtoByCycleId(dayId);
        }catch (EntityNotFoundException e){

            return -1;
        }
        CycleDto cycleDto = dayToEdit.getCycleDto();

        LocalDate startDay = cycleDto.getStartDay();
        LocalDate deliveryDate = startDay.plusDays(dayToEdit.getDayNumber()-1);

        forageDto.setDeliveryDate(deliveryDate);
        ForageDto forageToAdd = forageService.createForage(forageDto);
        dayToEdit.setForageDto(forageToAdd);

        cycleId = dayToEdit.getCycleDto().getId();
        dayRepository.save(dayMapper.mapTo(dayToEdit));

        return cycleId;
    }

    public long addMedicineToDay(MedicineDto medicineDto, Long dayId) {
        long cycleId;
        DayDto dayToEdit;
        try {
            dayToEdit = getDayDtoByCycleId(dayId);
        }catch (EntityNotFoundException e){

            return -1;
        }

        MedicineListDto medicineListDto = dayToEdit.getMedicineListDto();
        medicineDto = medicineService.createMedicine(medicineDto);
        if (medicineListDto == null){
            medicineListDto = new MedicineListDto();
            LocalDate startDay = dayToEdit.getCycleDto().getStartDay();
            LocalDate applicationDate = startDay.plusDays(dayToEdit.getDayNumber()-1);
            medicineListDto.setApplicationDate(applicationDate);
            medicineListDto = medicineListService.createMedicineList(medicineListDto);
        }
        medicineDto.setMedicineListDto(medicineListDto);

        medicineService.createMedicine(medicineDto);
        dayToEdit.setMedicineListDto(medicineListDto);
        dayRepository.save(dayMapper.mapTo(dayToEdit));

        cycleId = dayToEdit.getCycleDto().getId();

        return cycleId;
    }

    private DayDto getDayDtoByCycleId(Long dayId){
        return dayRepository.findById(dayId)
                .map(dayMapper::mapTo)
                .orElseThrow(EntityNotFoundException::new);
    }
}
