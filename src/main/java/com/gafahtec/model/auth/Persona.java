package com.gafahtec.model.auth;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@ToString(exclude = { "empleados" })
@EqualsAndHashCode(exclude = { "empleados" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Persona {

	@Id
	@Column(unique = true)
	private String numeroDocumento;
	private String tipoDocumento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String direccion;
	private String sexo;
	private LocalDateTime fechaIngreso;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime fechaRegistro;

	private String telefono;
	private String celular;

	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "persona", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Empleado> empleados = new HashSet<>();

}
