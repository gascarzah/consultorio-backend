package com.gafahtec.model.consultorio;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = { "citas" })
@EqualsAndHashCode(exclude = {  "citas" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class HistoriaClinica {

	@Id
	private String numeroHistoriaClinica;
	private String ectoscopia;
	private String alergia;
	private String motivo;
	private String antecedentesMedicos;

	@MapsId
	@JoinColumn(name = "numero_documento")
	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
//	@JsonIgnore
//	@Builder.Default
//	@OneToMany(mappedBy = "historiaClinica", cascade = { CascadeType.ALL }, orphanRemoval = true)
//	private Set<Cita> citas = new HashSet<>();
}
