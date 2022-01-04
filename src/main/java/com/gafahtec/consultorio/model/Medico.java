package com.gafahtec.consultorio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
public class Medico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMedico;
	@NotEmpty(message = "Campo nombres no puede estar vacio")
	private String nombres;
	@NotEmpty(message = "Campo apellido paterno no puede estar vacio")
	private String apellidoPaterno;
	@NotEmpty(message = "Campo apellido materno no puede estar vacio")
	private String apellidoMaterno;
	@NotEmpty(message = "Campo dni no puede estar vacio")
	private String dni;
	
	
	private String telefono;
	private String celular;
	
	private LocalDateTime fecha;
	
	@JsonIgnore
	@Builder.Default
	@OneToMany( mappedBy = "medico", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<Programacion> Programaciones  = new ArrayList<>();
	

}
