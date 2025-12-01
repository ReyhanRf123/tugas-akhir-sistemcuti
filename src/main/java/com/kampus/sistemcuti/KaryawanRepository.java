package com.kampus.sistemcuti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan, Long> {
    // Kosong saja, sudah otomatis punya fungsi CRUD (Simpan, Hapus, Cari)
}