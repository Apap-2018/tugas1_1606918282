package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;

public interface PegawaiService {
	Optional<PegawaiModel> getEmployeeDetailById(Integer id);
	List<PegawaiModel> allPegawai();
	void addEmployee(PegawaiModel employee);
	void deleteEmployee(PegawaiModel employee);
	PegawaiModel dataPegawai(String nip);
	int totalPegawai();
	
}
