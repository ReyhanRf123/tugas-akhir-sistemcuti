package com.kampus.sistemcuti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController // Menandakan ini adalah REST API (kembaliannya JSON)
@RequestMapping("/api")
public class CutiController {

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private PengajuanCutiRepository pengajuanCutiRepository;

    // 1. API untuk melihat semua karyawan (GET)
    @GetMapping("/karyawan")
    public List<Karyawan> getAllKaryawan() {
        return karyawanRepository.findAll();
    }

    // 2. API untuk melihat riwayat cuti (GET)
    @GetMapping("/riwayat-cuti")
    public List<PengajuanCuti> getAllCuti() {
        return pengajuanCutiRepository.findAll();
    }

    // 3. API Logic Pengajuan Cuti (POST) - INI BAGIAN BPM-NYA
    @PostMapping("/ajukan-cuti")
    public String ajukanCuti(@RequestBody PengajuanCuti pengajuan) {
        
        // Cari data karyawan berdasarkan ID yang dikirim
        Long idKaryawan = pengajuan.getKaryawan().getId();
        Optional<Karyawan> karyawanOpt = karyawanRepository.findById(idKaryawan);

        if (karyawanOpt.isEmpty()) {
            return "Error: Karyawan tidak ditemukan!";
        }

        Karyawan karyawan = karyawanOpt.get();

        // Hitung berapa hari cuti (Sederhana: anggap user input manual jumlah harinya dulu biar simpel)
        // Di sistem asli kita hitung selisih tanggal, tapi untuk pemula kita hitung manual nanti.
        int jumlahHariMinta = 1; // Anggap minta 1 hari dulu untuk tes simpel

        // --- START LOGIC BPM ---
        if (karyawan.getSisaCuti() >= jumlahHariMinta) {
            // Logic 1: Kurangi sisa cuti
            karyawan.setSisaCuti(karyawan.getSisaCuti() - jumlahHariMinta);
            karyawanRepository.save(karyawan); // Update ke database

            // Logic 2: Simpan riwayat cuti
            pengajuan.setKaryawan(karyawan); // Hubungkan relasi
            pengajuan.setStatus("DISETUJUI");
            pengajuanCutiRepository.save(pengajuan);

            return "Sukses! Cuti disetujui. Sisa cuti anda: " + karyawan.getSisaCuti();
        } else {
            // Logic 3: Tolak
            pengajuan.setKaryawan(karyawan);
            pengajuan.setStatus("DITOLAK - Sisa cuti habis");
            pengajuanCutiRepository.save(pengajuan);

            return "Gagal! Sisa cuti tidak mencukupi.";
        }
        // --- END LOGIC BPM ---
    }
}