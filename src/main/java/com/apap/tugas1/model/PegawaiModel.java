package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable,Comparable<PegawaiModel>  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "NIP", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempat_lahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tanggal_lahir", nullable = false)
	private String tanggal_lahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahun_masuk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "jabatan_pegawai",
            joinColumns = { @JoinColumn(name = "id_pegawai") },
            inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
    private List<JabatanModel> jabatanList;
	
	private String[] jabatan;
	
	private String ids_instansi;

	public String getIds_instansi() {
		return ids_instansi;
	}

	public void setIds_instansi(String ids_instansi) {
		this.ids_instansi = ids_instansi;
	}

	public String[] getJabatan() {
		return jabatan;
	}

	public void setJabatan(String[] jabatan) {
		this.jabatan = jabatan;
	}

	public List<JabatanModel> getJabatanList() {
		return jabatanList;
	}

	public void setJabatanList(List<JabatanModel> jabatanList) {
		this.jabatanList = jabatanList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempat_lahir() {
		return tempat_lahir;
	}

	public void setTempat_lahir(String tempat_lahir) {
		this.tempat_lahir = tempat_lahir;
	}

	public String getTanggal_lahir() {
		return tanggal_lahir;
	}

	public void setTanggal_lahir(String tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}

	public String getTahun_masuk() {
		return tahun_masuk;
	}

	public void setTahun_masuk(String tahun_masuk) {
		this.tahun_masuk = tahun_masuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}
	
	public double getGaji() {
		List<JabatanModel> listjabatan = this.getJabatanList();
		double gajiPokok = listjabatan.stream().mapToDouble(jabatan -> jabatan.getGaji_pokok()).sum();
		double tunjangan = this.getInstansi().getProvinsi().getPresentase_tunjangan();
		return gajiPokok * tunjangan ;
	}
	
	@Override
    public int compareTo(PegawaiModel other) {
		int this_year = Integer.parseInt(this.getTanggal_lahir().split("-")[0]);
		int other_year = Integer.parseInt(other.getTanggal_lahir().split("-")[0]);
        return this_year > other_year ? -1 : 1;
    }
}
