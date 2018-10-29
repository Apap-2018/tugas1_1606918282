package com.apap.tugas1.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Integer> {
	PegawaiModel findByNip(String nip);
//	List<PegawaiModel> findByInstansiAndTanggal_lahirAndTahun_masuk(InstansiModel instansi, Date tanggal_lahir, String tahun_masuk);
	
}
