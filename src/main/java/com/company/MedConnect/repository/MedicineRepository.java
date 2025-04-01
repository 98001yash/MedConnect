package com.company.MedConnect.repository;

import com.company.MedConnect.entities.Medicine;
import com.company.MedConnect.enums.MedicineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    List<Medicine> findByStatus(MedicineStatus status);
}
