package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	Optional<JabatanModel> getJabatanDetailById(Integer id);
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	List<JabatanModel> allJabatan();
}
