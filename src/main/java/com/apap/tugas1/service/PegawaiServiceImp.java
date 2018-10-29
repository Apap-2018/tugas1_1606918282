package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImp implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public Optional<PegawaiModel> getEmployeeDetailById(Integer id) {
		// TODO Auto-generated method stub
		return pegawaiDb.findById(id);
	}

	@Override
	public void addEmployee(PegawaiModel employee) {
		// TODO Auto-generated method stub
		pegawaiDb.save(employee);
		
	}

	@Override
	public void deleteEmployee(PegawaiModel employee) {
		// TODO Auto-generated method stub
		pegawaiDb.delete(employee);
	}

	@Override
	public PegawaiModel dataPegawai(String nip) {
		return pegawaiDb.findByNip(nip);
		
	}
	
	@Override
	public int totalPegawai() {
		return pegawaiDb.findAll().size();
	}
	
	@Override
	public List<PegawaiModel> allPegawai() {
		return pegawaiDb.findAll();
	}

}
