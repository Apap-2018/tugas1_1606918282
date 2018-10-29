package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;

public interface PosisiService {
	Optional<JabatanModel> getPositionDetailById(Integer id);
	
	void addPosition(JabatanModel position);
	void deletePosition(JabatanModel position);
	List<JabatanModel> allPositions();
}
