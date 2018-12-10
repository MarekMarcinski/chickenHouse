package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.MedicineDto;
import org.marcinski.chickenHouse.entity.Medicine;
import org.marcinski.chickenHouse.mapper.MedicineMapper;
import org.marcinski.chickenHouse.repository.MedicineRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    private MedicineRepository medicineRepository;
    private MedicineMapper medicineMapper;

    public MedicineService(MedicineRepository medicineRepository, MedicineMapper medicineMapper) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
    }

    public MedicineDto createMedicine(MedicineDto medicineDto) {
        Medicine medicine = medicineRepository.save(medicineMapper.mapTo(medicineDto));
        return medicineMapper.mapTo(medicine);
    }
}
