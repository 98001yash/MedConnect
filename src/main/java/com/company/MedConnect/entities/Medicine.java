package com.company.MedConnect.entities;


import com.company.MedConnect.enums.MedicineStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;

    private int quantity;
    private LocalDate expiryDate;
    private String location;


    @Enumerated(EnumType.STRING)
    private MedicineStatus status;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;
}
