package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.ForageDto;
import org.marcinski.chickenHouse.entity.Day;
import org.marcinski.chickenHouse.entity.Forage;
import org.marcinski.chickenHouse.mapper.ForageMapper;
import org.marcinski.chickenHouse.repository.DayRepository;
import org.marcinski.chickenHouse.repository.ForageRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ForageService {

    private ForageRepository forageRepository;
    private DayRepository dayRepository;
    private ForageMapper forageMapper;

    public ForageService(ForageRepository forageRepository,
                         ForageMapper forageMapper,
                         DayRepository dayRepository) {
        this.forageRepository = forageRepository;
        this.forageMapper = forageMapper;
        this.dayRepository = dayRepository;
    }

    public ForageDto createForage(ForageDto forageDto){
        Forage saved = forageRepository.save(forageMapper.mapTo(forageDto));
        return forageMapper.mapTo(saved);
    }

    public long deleteForage(Long id) {
        Forage toRemove = forageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        long dayId = toRemove.getDay().getId();

        Day day = dayRepository.findById(dayId).orElseThrow(EntityNotFoundException::new);
        day.setForage(null);
        dayRepository.save(day);

        forageRepository.deleteById(id);
        return toRemove.getDay().getCycle().getId();
    }
}
