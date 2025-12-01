package com.kampus.sistemcuti;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok: Otomatis bikin Getter, Setter, toString, dll
@Table(name = "karyawan")
public class Karyawan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String jabatan;
    
    // Jatah cuti awal, misal 12 hari setahun
    private int sisaCuti; 
}