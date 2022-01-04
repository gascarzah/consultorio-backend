package com.gafahtec.consultorio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.ToString;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString(exclude = { "departamentos", "cocheras", "vehiculos" })
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaciente;
	@NotEmpty(message = "Campo nombres no puede estar vacio")
	private String nombres;
	@NotEmpty(message = "Campo apellido paterno no puede estar vacio")
	private String apellidoPaterno;
	@NotEmpty(message = "Campo apellido materno no puede estar vacio")
	private String apellidoMaterno;
	@NotEmpty(message = "Campo dni no puede estar vacio")
	private String dni;
	
	private String direccion;
	private String telefono;
	private String celular;
	
	private LocalDateTime fecha;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_historia_clinica", referencedColumnName = "idHistoriaClinica")
	private HistoriaClinica historiaClinica;
	
	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "paciente", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<Consulta> citas  = new ArrayList<>();
	
}
