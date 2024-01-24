package com.gafahtec.model.consultorio;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = { "programacionDetalles" })
@ToString(exclude = { "programacionDetalles" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Programacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProgramacion;


	
	private Date fechaInicial;
	private Date fechaFinal;

	private String strFechaInicial;
	private String strFechaFinal;

	private String rango;
	private Boolean activo;
//	private Boolean estado;

	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "programacion", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<ProgramacionDetalle> programacionDetalles = new HashSet<>();;

}
