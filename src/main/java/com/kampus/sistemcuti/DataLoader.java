package com.kampus.sistemcuti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private PengajuanCutiRepository pengajuanCutiRepository; // Tambahan 1: Panggil repo pengajuan

    @Override
    public void run(String... args) throws Exception {
        // Cek jika data kurang dari 10, kita reset.
        if (karyawanRepository.count() < 10) {
            System.out.println("Data karyawan sedikit, mereset data dummy...");
            
            // PERBAIKAN DISINI:
            // Hapus data 'Anak' (Pengajuan Cuti) dulu, baru hapus 'Bapak' (Karyawan)
            pengajuanCutiRepository.deleteAll(); 
            karyawanRepository.deleteAll(); 

            // Daftar Nama Dummy (15 Orang)
            String[] namaNama = {
                "Budi Santoso", "Siti Aminah", "Rudi Hartono", "Dewi Lestari", 
                "Eko Prasetyo", "Fajar Nugraha", "Gita Gutawa", "Hendra Kurniawan",
                "Indah Permata", "Joko Anwar", "Kartika Sari", "Lukman Hakim",
                "Maya Septha", "Nanda Putra", "Oscar Lawalata"
            };

            String[] jabatanList = {"Staff IT", "HRD", "Finance", "Marketing", "Manager"};
            Random rand = new Random();

            for (String nama : namaNama) {
                Karyawan k = new Karyawan();
                k.setNama(nama);
                String jabatanAcak = jabatanList[rand.nextInt(jabatanList.length)];
                k.setJabatan(jabatanAcak);
                k.setSisaCuti(10 + rand.nextInt(5)); 
                
                karyawanRepository.save(k);
            }

            System.out.println("Berhasil generate 15 Data Karyawan Dummy!");
        }
    }
}