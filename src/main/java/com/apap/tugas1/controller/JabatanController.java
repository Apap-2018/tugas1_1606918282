package com.apap.tugas1.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class JabatanController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
	
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("title", "Tambah Jabatan");
		return "tambahJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("title", "Tambah Jabatan");
		return "tambah";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String getJabatan(@RequestParam("idJabatan") String id,Model model) {
		JabatanModel jm = jabatanService.getJabatanDetailById(Integer.parseInt(id)).orElse(null);
		
		System.out.println(jm);
		model.addAttribute("jabatan",jm);
		model.addAttribute("title", "Detail Jabatan");
		
		return "detilJabatan";	
	}

	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String daftar(Model model) {
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("title", "Lihat Semua Jabatan");
		return "daftarJabatan";
	}
	
	
	@RequestMapping(value = "/jabatan/jumlah", method = RequestMethod.GET)
	private String jumlah(Model model) {
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("title", "Jumlah Pegawai");
		return "jumlahPegawai";
	}	
	
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String ubah(@RequestParam("idJabatan")String id, Model model) {
		JabatanModel updated = jabatanService.getJabatanDetailById(Integer.parseInt(id)).orElse(null);
		model.addAttribute("jabatan", updated);
		model.addAttribute("title", "Ubah Data Jabatan");
		return "ubahJabatan";
	}
	
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String ubahSubmit(@RequestParam("idJabatan")String id, @ModelAttribute JabatanModel jabatan,Model model) {
		JabatanModel updated = jabatanService.getJabatanDetailById(Integer.parseInt(id)).orElse(null);
		updated.setNama(jabatan.getNama());
		updated.setDeskripsi(jabatan.getDeskripsi());
		updated.setGaji_pokok(jabatan.getGaji_pokok());
		
		jabatanService.addJabatan(updated);
		model.addAttribute("jabatan", updated);
		return "ubah";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.GET)
	private String deleteSubmit(@RequestParam("idJabatan")String id, Model model) {
		JabatanModel deleted = jabatanService.getJabatanDetailById(Integer.parseInt(id)).orElse(null);
		
		System.out.println(deleted.getPegawaiList());
		
		if(deleted.getPegawaiList().isEmpty()) {
			jabatanService.deleteJabatan(deleted);
			model.addAttribute("status","Jabatan berhasil di hapus");
		}else {
			model.addAttribute("status","Jabatan tidak dapat di hapus");
		}
		model.addAttribute("title", "Hapus Jabatan");
		return "hapus";
		
	}

}
