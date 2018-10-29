package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		List<ProvinsiModel> provinsis = provinsiService.allProvinces();
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("model",provinsis);
		model.addAttribute("models",jabatan);
		return "home";
	}
	
	@RequestMapping(value= "/pegawai", method=RequestMethod.GET)
	private String dataPegawai(@RequestParam("nip") String nip,Model model) {
		PegawaiModel dataPegawai = pegawaiService.dataPegawai(nip);
		model.addAttribute("pegawai",dataPegawai);
		model.addAttribute("gaji",(int)dataPegawai.getGaji());
		return "dataPegawai";
	}
	
	@RequestMapping(value= "/pegawai/tambah", method=RequestMethod.GET)
	private String dataPegawai(Model model) {
		List<ProvinsiModel> provinsi = provinsiService.allProvinces();
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		int idPegawai = pegawaiService.totalPegawai() + 1;
		model.addAttribute("provinsi",provinsi);
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("idPegawai",idPegawai);
		return "tambahPegawai";
	}
	
	@RequestMapping(value= "/pegawai/tambah/{instanceId}", method=RequestMethod.POST)
	public @ResponseBody 
	String tambahPegawai(@RequestBody Pegawai pegawai,@PathVariable(value = "instanceId") String id,Model model){
		
		PegawaiModel pegawaimdl = pegawai.getPegawai();
		InstansiModel instanmdl = instansiService.getInstanceDetailById(Integer.parseInt(id)).orElse(null);
		pegawaimdl.setInstansi(instanmdl);
		List<JabatanModel> listJabatan = new ArrayList<JabatanModel>();
		for (String s : pegawaimdl.getJabatan()) {
			listJabatan.add(jabatanService.getJabatanDetailById(Integer.parseInt(s)).orElse(null));
		}
		
		String tgl = pegawaimdl.getTanggal_lahir();
		String msk = pegawaimdl.getTahun_masuk();
		
		
		List<PegawaiModel> filter = pegawaiService.allPegawai();
		filter = filter.stream()
			.filter(peg -> peg.getInstansi().getId() == pegawaimdl.getInstansi().getId())
			.filter(peg->peg.getTanggal_lahir().equalsIgnoreCase(tgl))
			.filter(peg->peg.getTahun_masuk().equalsIgnoreCase(msk))
			.collect(Collectors.toList());
		
		List<PegawaiModel> npms = filter.stream().filter(peg->peg.getNip().contains(pegawai.getPegawai().getNip())).collect(Collectors.toList());
		
		String[] st_npm = new String[npms.size()];
		
		
		for(int i=0;i<npms.size();i++) {
			st_npm[i] = npms.get(i).getNip().substring(14,16);
		}
		
		
		Arrays.sort(st_npm,Collections.reverseOrder());
		
		for(String pm : st_npm) {
			System.out.println(pm);
		}
		
		int urutanPegawai = 0;

		if (filter.isEmpty()) {
			urutanPegawai += 1;
		} else {
			System.out.println(npms.size());
			urutanPegawai = Integer.parseInt(st_npm[0]) + 1;
		}

		String nip = pegawaimdl.getNip();


		if(urutanPegawai < 10){
			nip +=  "0" + String.valueOf(urutanPegawai);
		} else {
			nip += String.valueOf(urutanPegawai);
		}


		pegawaimdl.setNip(nip);
		pegawaimdl.setJabatanList(listJabatan);
		pegawaiService.addEmployee(pegawaimdl);
		return "Pegawai dengan NIP " + pegawaimdl.getNip() + " berhasil ditambahkan";
	}
	

	@RequestMapping(value= "/pegawai/tambah/{id}", method=RequestMethod.GET)
	private String statusPegawai(Pegawai pegawai,@PathVariable(value = "id") String id, Model model) {
		PegawaiModel pegawaimdl = pegawaiService.getEmployeeDetailById(Integer.parseInt(id)).orElse(null);
		return "suksesTambah";
	}
	
	
	@RequestMapping(value= "/get/instansi/{provinsi}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> bar(@PathVariable(value = "provinsi") String provinsi) {
	    final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    ProvinsiModel province = provinsiService.getProvinceDetailById(Integer.parseInt(provinsi)).orElse(null);
	    List<InstansiModel> instances = instansiService.allInstances(province);
	    StringBuilder sb = new StringBuilder();
	    for (InstansiModel s : instances)
	    {
	    	sb.append(s.getId());
	    	sb.append(" // ");
	        sb.append(s.getNama());
	        sb.append(" // ");
	    }

	    return new ResponseEntity<String>("{\"instansi\": \""+ sb.toString() + "\"}", httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value= "/pegawai/ubah", method=RequestMethod.GET)
	private String ubahDataPegawai(@RequestParam("nip") String nip,Model model) {
		PegawaiModel dataPegawai = pegawaiService.dataPegawai(nip);
		List<ProvinsiModel> provinsi = provinsiService.allProvinces();
		List<JabatanModel> jabatan = jabatanService.allJabatan();
		model.addAttribute("provinsi",provinsi);
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("pegawai",dataPegawai);
		return "ubahPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah/nip={nip}", method = RequestMethod.POST)
	 private @ResponseBody 
	 String updateDealerSubmit(@RequestBody Pegawai pegawai, @PathVariable(value = "nip") String nip) {
		PegawaiModel updated = pegawaiService.dataPegawai(nip);
		InstansiModel newInstansi = instansiService.getInstanceDetailById(Integer.parseInt(pegawai.getPegawai().getIds_instansi())).orElse(null);
		System.out.println(newInstansi.getNama());
		
		updated.setNama(pegawai.getPegawai().getNama());
		updated.setTempat_lahir(pegawai.getPegawai().getTempat_lahir());
		
		boolean updateNip = false;
		
		System.out.println(!pegawai.getPegawai().getTanggal_lahir().equals(updated.getTanggal_lahir()));
		System.out.println(!pegawai.getPegawai().getTahun_masuk().equals(updated.getTahun_masuk()));
		System.out.println(newInstansi.getId());
		System.out.println(updated.getInstansi().getId());
		
		if(!pegawai.getPegawai().getTanggal_lahir().equals(updated.getTanggal_lahir()) || !pegawai.getPegawai().getTahun_masuk().equals(updated.getTahun_masuk()) || newInstansi.getId() != updated.getInstansi().getId()) {
			
			updateNip = true;
			
			updated.setTanggal_lahir(pegawai.getPegawai().getTanggal_lahir());
			updated.setTahun_masuk(pegawai.getPegawai().getTahun_masuk());
			updated.setInstansi(newInstansi);
		}
		
		List<JabatanModel> listJabatan = new ArrayList<JabatanModel>();

		for (String s : pegawai.getPegawai().getJabatan()) {
			listJabatan.add(jabatanService.getJabatanDetailById(Integer.parseInt(s)).orElse(null));
		}

		String tgl = pegawai.getPegawai().getTanggal_lahir();
		String msk = pegawai.getPegawai().getTahun_masuk();

		List<PegawaiModel> filter = pegawaiService.allPegawai();

		filter = filter.stream()
				.filter(peg -> peg.getInstansi().getId() == newInstansi.getId())
				.filter(peg -> peg.getTanggal_lahir().equals(tgl))
				.filter(peg->peg.getTahun_masuk().equals(msk))
				.collect(Collectors.toList());
		
		List<PegawaiModel> npms = filter.stream().filter(peg->peg.getNip().contains(pegawai.getPegawai().getNip())).collect(Collectors.toList());
		
		String[] st_npm = new String[npms.size()];
		
		
		for(int i=0;i<npms.size();i++) {
			st_npm[i] = npms.get(i).getNip().substring(14,16);
		}
		
		
		Arrays.sort(st_npm,Collections.reverseOrder());
		
		for(String pm : st_npm) {
			System.out.println(pm);
		}
		
		
		int urutanPegawai = 0;

		if (filter.isEmpty()) {
			urutanPegawai += 1;
		} else {
			System.out.println(npms.size());
			if(npms.size() > 0 ) {
				urutanPegawai = Integer.parseInt(st_npm[0]) + 1;
			}
		}

		
		String newnip = pegawai.getPegawai().getNip();


		if(urutanPegawai < 10){
			newnip +=  "0" + String.valueOf(urutanPegawai);
		} else {
			newnip += String.valueOf(urutanPegawai);
		}

		System.out.println(newnip);
		System.out.println(updateNip);

		if(updateNip) {
			updated.setNip(newnip);
		}
		
		updated.setJabatanList(listJabatan);
		pegawaiService.addEmployee(updated);

		return "Pegawai dengan NIP " + updated.getNip() + " berhasil diubah";
	 }
	
	
	
	 @RequestMapping(value = "/pegawai/carii", method = RequestMethod.GET)
	    private String cariPegawai(@RequestParam("idProvinsi") int idProvinsi, @RequestParam("idInstansi") int idInstansi, @RequestParam("idJabatan") int idJabatan, Model model) {
	        List<PegawaiModel> filter = pegawaiService.allPegawai();
	        if (idProvinsi != 0) {
	            filter = filter.stream()
	                    .filter(pegawai -> pegawai.getInstansi().getProvinsi().getId() == idProvinsi).collect(Collectors.toList());
	        }
	        if (idInstansi != 0) {
	            filter = filter.stream()
	                    .filter(pegawai -> pegawai.getInstansi().getId() == idInstansi).collect(Collectors.toList());
	        }

	        if (idJabatan != 0) {
	            filter = filter.stream()
	                    .filter(pegawai -> pegawai.getJabatanList().stream()
	                            .anyMatch(jabatans -> jabatans.getId() == idJabatan)).collect(Collectors.toList());
	        }

	        List<ProvinsiModel> provinsi = provinsiService.allProvinces();
	        List<JabatanModel> jabatan = jabatanService.allJabatan();
	        model.addAttribute("provinsi", provinsi);
	        model.addAttribute("jabatan", jabatan);
	        model.addAttribute("pegawai", filter);
	        return "cariPegawai";
	    }

	    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	    private String filterPegawai(Model model) {
	        List<ProvinsiModel> provinsi = provinsiService.allProvinces();
	        List<JabatanModel> jabatan = jabatanService.allJabatan();
	        model.addAttribute("provinsi", provinsi);
	        model.addAttribute("jabatan", jabatan);
	        return "cariPegawai";
	    }
	
	@RequestMapping(value= "/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String pegawaiTermudaTertua(@RequestParam("idProvinsi") int idProvinsi,@RequestParam("idInstansi") int idInstansi,Model model) {
		List<PegawaiModel> filter = pegawaiService.allPegawai();
		filter = filter.stream()
			.filter(pegawai -> pegawai.getInstansi().getProvinsi().getId() == idProvinsi)				
			.filter(pegawai -> pegawai.getInstansi().getId() == idInstansi)
			.collect(Collectors.toList());
		Collections.sort(filter);
		model.addAttribute("pegawaiTermuda",filter.get(0));
		model.addAttribute("pegawaiTertua",filter.get(filter.size()-1));
		return "pegawaiTermudaTertua";
	}
	
}


class Pegawai {   

    PegawaiModel pegawai;
    

    public PegawaiModel getPegawai() {
        return pegawai;
    }
   
    
}


