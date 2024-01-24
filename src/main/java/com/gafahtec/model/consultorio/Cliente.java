package com.gafahtec.model.consultorio;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
public class Cliente {

	@Id
	private String numeroDocumento;

	private String nombres;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String tipoDocumento;

	private String direccion;

	private String telefono;
	private String celular;

	private String email;

	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "cliente", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Cita> citas = new HashSet<>();

//	@JsonIgnore
//	@Builder.Default
//	@OneToMany(mappedBy = "cliente", cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
//	private Set<Matricula> matriculas = new HashSet<>();

}
