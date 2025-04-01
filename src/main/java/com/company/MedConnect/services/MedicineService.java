package com.company.MedConnect.services;


import com.company.MedConnect.entities.Medicine;
import com.company.MedConnect.enums.MedicineStatus;
import com.company.MedConnect.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public Medicine donateMedicine(Medicine medicine){
        medicine.setStatus(MedicineStatus.AVAILABLE);
        return medicineRepository.save(medicine);
    }

    public List<Medicine> getAvailableMedicines(){
        return medicineRepository.findByStatus(MedicineStatus.AVAILABLE);
    }

    public Optional<Medicine> getMedicineById(Long id){
        return medicineRepository.findById(id);
    }

    public Medicine requestMedicine(Long id){
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Medicine not found"));

        if(medicine.getStatus()!= MedicineStatus.AVAILABLE){
            throw new RuntimeException("Medicine is not available");
        }
        medicine.setStatus(MedicineStatus.REQUESTED);
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id){
        medicineRepository.deleteById(id);
    }

}
