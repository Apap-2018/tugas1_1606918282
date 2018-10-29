package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.JabatanPegawaiDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb epDb;

	@Override
	public List<JabatanModel> allJabatan() {
		// TODO Auto-generated method stub
		return epDb.findAll();
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		epDb.save(jabatan);
	}
	
	@Override
	public Optional<JabatanModel> getJabatanDetailById(Integer id) {
		// TODO Auto-generated method stub
		return epDb.findById(id);
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		epDb.delete(jabatan);
		
	}
}
