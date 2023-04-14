package com.gafahtec.consultorio.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
//@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@ToString(exclude = { "historiaClinicaDetalles" })
public class HistoriaClinica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idHistoriaClinica;
	
	private String ectoscopia;
	private String alergia;
	private String motivo;
	private String antecedentesMedicos;

	@JsonIgnore
	 @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cliente", referencedColumnName = "idCliente")
	private Cliente cliente;

//	@JsonIgnore
//	@Builder.Default
//	@OneToMany( mappedBy = "historiaClinica", cascade = { CascadeType.ALL }, orphanRemoval = true)
//	private Set<HistoriaClinicaDetalle> historiaClinicaDetalles  = new HashSet();
}
