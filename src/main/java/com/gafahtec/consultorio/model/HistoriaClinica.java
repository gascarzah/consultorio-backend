package com.gafahtec.consultorio.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoriaClinica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idHistoriaClinica;
	
	private String ectoscopia;
	private String alergia;
	private String motivo;
	private String antecedentesMedicos;
	
	@OneToOne(mappedBy = "historiaClinica")
	private Paciente paciente;
	
	@JsonIgnore
	@Builder.Default
	@OneToMany( mappedBy = "historiaClinica", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<HistoriaClinicaDetalle> historiaClinicaDetalles  = new ArrayList<>();
}
