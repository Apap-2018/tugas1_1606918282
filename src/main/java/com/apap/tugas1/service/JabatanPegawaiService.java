package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanPegawaiModel;

public interface JabatanPegawaiService {
	Optional<JabatanPegawaiModel> getEmployeePosDetailById(Integer id);
	
	void addEmpPos(JabatanPegawaiModel ep);
	void deleteEmpPos(JabatanPegawaiModel ep);
	List<JabatanPegawaiModel> allEmpPos();
}
