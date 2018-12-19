package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.ChickenHouseDto;
import org.marcinski.chickenHouse.dto.CycleDto;
import org.marcinski.chickenHouse.entity.Cycle;
import org.marcinski.chickenHouse.mapper.CycleMapper;
import org.marcinski.chickenHouse.repository.CycleRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CycleService {

    private CycleRepository cycleRepository;
    private ChickenHouseService chickenHouseService;
    private CycleMapper cycleMapper;

    public CycleService(CycleRepository cycleRepository, ChickenHouseService chickenHouseService, CycleMapper cycleMapper) {
        this.cycleRepository = cycleRepository;
        this.chickenHouseService = chickenHouseService;
        this.cycleMapper = cycleMapper;
    }

    public void createCycle(CycleDto cycleDto, Long id) {
        ChickenHouseDto chickenHouseById = chickenHouseService.getChickenHouseById(id);

        cycleDto.setChickenHouseDto(chickenHouseById);

        Cycle cycle = cycleMapper.mapTo(cycleDto);
        cycleRepository.save(cycle);
    }

    public List<CycleDto> getAllByChickenHouseIdOrderedByStartDayDesc(Long id) {
        List<Cycle> cycles = cycleRepository.findAllByChickenHouseIdOrderByStartDayDesc(id);
        return cycles.stream()
                .map(cycleMapper::mapTo)
                .collect(Collectors.toList());
    }

    public long updateCycle(CycleDto cycleDto, Long id) {
        Optional<Cycle> byId = cycleRepository.findById(id);
        long chickenHouseId = 0;
        if (byId.isPresent()){
            Cycle cycleToEdit = byId.get();
            cycleToEdit.setStartDay(cycleDto.getStartDay());
            cycleToEdit.setNumberOfChickens(cycleDto.getNumberOfChickens());
            cycleToEdit.setHatchery(cycleDto.getHatchery());
            cycleToEdit.setHybrid(cycleDto.getHybrid());
            cycleRepository.save(cycleToEdit);
            chickenHouseId = cycleToEdit.getChickenHouse().getId();
        }
        return chickenHouseId;
    }

    public long completeCycle(Long id) {
        Optional<Cycle> byId = cycleRepository.findById(id);
        long chickenHouseId = 0;
        if (byId.isPresent()){
            Cycle cycleToEdit = byId.get();
            cycleToEdit.setCompleted(true);
            cycleRepository.save(cycleToEdit);
            chickenHouseId = cycleToEdit.getChickenHouse().getId();
        }
        return chickenHouseId;
    }

    public CycleDto getDtoById(Long id) {
        return cycleRepository.findById(id)
                .map(cycleMapper::mapTo)
                .orElseThrow(EntityNotFoundException::new);
    }

    public boolean isUserCycle(Principal principal, CycleDto cycleDto) {
        ChickenHouseDto chickenHouseById = chickenHouseService.getChickenHouseById(cycleDto.getChickenHouseDto().getId());
        String emailFromPrincipal = principal.getName();
        String emailFromCycle = chickenHouseById.getUserDto().getEmail();

        if (!emailFromCycle.equals(emailFromPrincipal)){
            return false;
        }
        return true;
    }
}
