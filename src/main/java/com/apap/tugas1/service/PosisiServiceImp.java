package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class PosisiServiceImp implements PosisiService {
	@Autowired
	private JabatanDb positionDb;

	@Override
	public Optional<JabatanModel> getPositionDetailById(Integer id) {
		// TODO Auto-generated method stub
		return positionDb.findById(id);
	}

	@Override
	public void addPosition(JabatanModel position) {
		// TODO Auto-generated method stub
		positionDb.save(position);
	}

	@Override
	public void deletePosition(JabatanModel position) {
		// TODO Auto-generated method stub
		positionDb.delete(position);
	}

	@Override
	public List<JabatanModel> allPositions() {
		// TODO Auto-generated method stub
		return positionDb.findAll();
	}
	
	
}
