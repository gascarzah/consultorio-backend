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
public class Consulta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsulta;
	@ManyToOne
	@JoinColumn(name = "id_programacion", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_programacion"))
	private Programacion programacion;
	
	@ManyToOne
	@JoinColumn(name = "id_paciente", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_paciente"))
	private Paciente paciente;
}
