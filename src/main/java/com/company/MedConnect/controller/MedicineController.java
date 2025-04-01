package com.company.MedConnect.controller;


import com.company.MedConnect.entities.Medicine;
import com.company.MedConnect.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;


    @PostMapping("/donate")
    public ResponseEntity<Medicine> donateMedicine(@RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineService.donateMedicine(medicine));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Medicine>> getAvailableMedicines() {
        return ResponseEntity.ok(medicineService.getAvailableMedicines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/request/{id}")
    public ResponseEntity<Medicine> requestMedicine(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.requestMedicine(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
