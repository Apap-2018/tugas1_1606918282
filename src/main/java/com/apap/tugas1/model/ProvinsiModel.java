package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "provinsi")
public class ProvinsiModel implements Serializable {
	@Id
	@Size(max = 20)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Column(name = "presentase_tunjangan", nullable = false)
	private Double presentase_tunjangan;
	
	@OneToMany(mappedBy = "provinsi", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<InstansiModel> instansiList;

	public List<InstansiModel> getInstansiList() {
		return instansiList;
	}

	public void setInstansiList(List<InstansiModel> instansiList) {
		this.instansiList = instansiList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Double getPresentase_tunjangan() {
		return presentase_tunjangan;
	}

	public void setPresentase_tunjangan(Double presentase_tunjangan) {
		this.presentase_tunjangan = presentase_tunjangan;
	}


	
}
