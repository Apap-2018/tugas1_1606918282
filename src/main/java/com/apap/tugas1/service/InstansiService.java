package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	Optional<InstansiModel> getInstanceDetailById(Integer id);
	void addInstance(InstansiModel instance);
	void deleteInstance(InstansiModel instance);
	List<InstansiModel> allInstances(ProvinsiModel province);
}
