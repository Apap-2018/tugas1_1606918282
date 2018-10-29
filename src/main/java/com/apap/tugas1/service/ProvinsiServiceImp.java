package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImp implements ProvinsiService {
	@Autowired
	private ProvinsiDb provinceDb;

	@Override
	public Optional<ProvinsiModel> getProvinceDetailById(Integer id) {
		// TODO Auto-generated method stub
		return provinceDb.findById(id);
	}

	@Override
	public void addProvince(ProvinsiModel province) {
		// TODO Auto-generated method stub
		provinceDb.save(province);
	}

	@Override
	public void deleteProvince(ProvinsiModel province) {
		// TODO Auto-generated method stub
		provinceDb.delete(province);
	}

	@Override
	public List<ProvinsiModel> allProvinces() {
		// TODO Auto-generated method stub
		return provinceDb.findAll();
	}
}
