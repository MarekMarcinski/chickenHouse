package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.MedicineListDto;
import org.marcinski.chickenHouse.entity.MedicineList;
import org.marcinski.chickenHouse.mapper.MedicineListMapper;
import org.marcinski.chickenHouse.repository.MedicineListRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicineListService {

    private MedicineListRepository medicineListRepository;
    private MedicineListMapper medicineListMapper;

    public MedicineListService(MedicineListRepository medicineListRepository, MedicineListMapper medicineListMapper) {
        this.medicineListRepository = medicineListRepository;
        this.medicineListMapper = medicineListMapper;
    }

    public MedicineListDto createMedicineList(MedicineListDto medicineListDto) {
        MedicineList medicineList = medicineListRepository.save(medicineListMapper.mapTo(medicineListDto));
        return medicineListMapper.mapTo(medicineList);
    }
}
