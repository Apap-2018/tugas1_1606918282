package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImp implements InstansiService {
	@Autowired
	private InstansiDb instanceDb;

	@Override
	public Optional<InstansiModel> getInstanceDetailById(Integer id) {
		// TODO Auto-generated method stub
		return instanceDb.findById(id);
	}

	@Override
	public void addInstance(InstansiModel instance) {
		// TODO Auto-generated method stub
		instanceDb.save(instance);
	}

	@Override
	public void deleteInstance(InstansiModel instance) {
		// TODO Auto-generated method stub
		instanceDb.delete(instance);
	}

	@Override
	public List<InstansiModel> allInstances(ProvinsiModel province) {
		// TODO Auto-generated method stub
		List<InstansiModel> instances = instanceDb.findAll();
		return instances.stream().filter(instance -> instance.getProvinsi().getId() == province.getId()).collect(Collectors.toList());
	}
}
