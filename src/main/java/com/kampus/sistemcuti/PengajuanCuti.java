package com.kampus.sistemcuti;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "pengajuan_cuti")
public class PengajuanCuti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relasi: Banyak pengajuan cuti dimiliki oleh 1 Karyawan
    @ManyToOne 
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;

    private LocalDate tanggalMulai;
    private LocalDate tanggalSelesai;
    
    private String alasan;
    
    // Status: MENUNGGU, DISETUJUI, DITOLAK
    private String status; 
}