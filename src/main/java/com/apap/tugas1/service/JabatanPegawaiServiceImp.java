package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;

@Service
@Transactional
public class JabatanPegawaiServiceImp implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDb epDb;

	@Override
	public Optional<JabatanPegawaiModel> getEmployeePosDetailById(Integer id) {
		// TODO Auto-generated method stub
		return epDb.findById(id);
	}

	@Override
	public void addEmpPos(JabatanPegawaiModel ep) {
		// TODO Auto-generated method stub
		epDb.save(ep);
	}

	@Override
	public void deleteEmpPos(JabatanPegawaiModel ep) {
		// TODO Auto-generated method stub
		epDb.delete(ep);
	}

	@Override
	public List<JabatanPegawaiModel> allEmpPos() {
		// TODO Auto-generated method stub
		return epDb.findAll();
	}
	
}
