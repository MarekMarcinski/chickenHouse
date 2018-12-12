package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.MedicineDto;
import org.marcinski.chickenHouse.entity.Medicine;
import org.marcinski.chickenHouse.entity.MedicineList;
import org.marcinski.chickenHouse.mapper.MedicineMapper;
import org.marcinski.chickenHouse.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class MedicineService {

    private MedicineRepository medicineRepository;
    private MedicineMapper medicineMapper;

    public MedicineService(MedicineRepository medicineRepository,
                           MedicineMapper medicineMapper) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
    }

    public MedicineDto createMedicine(MedicineDto medicineDto) {
        Medicine savedMedicine = medicineRepository.save(medicineMapper.mapTo(medicineDto));
        return medicineMapper.mapTo(savedMedicine);
    }

    public long deleteMedicine(long id) {
        Medicine medicineToDelete = medicineRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        MedicineList medicineList = medicineToDelete.getMedicineList();
        Long cycleId = medicineList.getDay().getCycle().getId();

        medicineRepository.delete(medicineToDelete);
        return cycleId;
    }
}
