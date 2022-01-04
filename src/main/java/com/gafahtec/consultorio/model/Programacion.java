package com.gafahtec.consultorio.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Programacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProgramacion;
	private int dia;
	private String horario;
	private LocalDateTime fecha;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "id_medico", nullable = false, foreignKey = @ForeignKey(name = "FK_medico"))
	private Medico medico;
	 
	 
}
