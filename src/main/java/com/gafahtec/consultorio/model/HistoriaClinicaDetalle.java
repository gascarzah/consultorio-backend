package com.gafahtec.consultorio.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
public class HistoriaClinicaDetalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idHistoriaClinicaDetalle;
	
	@ManyToOne
	@JoinColumn(name = "id_tratamiento", nullable = true, foreignKey = @ForeignKey(name = "FK_historia_clinica_tratamiento"))
	private Tratamiento tratamiento;
	private boolean pagado;
	
	@ManyToOne
	@JoinColumn(name = "id_historia_clinica", nullable = true, foreignKey = @ForeignKey(name = "FK_hc_hcdet"))
	private HistoriaClinica historiaClinica;
}
