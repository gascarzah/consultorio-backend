package com.gafahtec.model.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@ToString(exclude = { "usuarios" })
@EqualsAndHashCode(exclude = { "usuarios" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Empleado {

	@EmbeddedId
	private EmpleadoId idEmpleado;

	@MapsId("numeroDocumento")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numero_documento")
    private Persona persona;

	@MapsId("idEmpresa")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa")
    private Empresa empresa;
	
    @ManyToOne
    @JoinColumn(name = "id_tipo_empleado", nullable = true)
    private TipoEmpleado tipoEmpleado;

    private Boolean activo;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "empleado", cascade = { CascadeType.ALL }, orphanRemoval = true)  
    private Set<Usuario> usuarios = new HashSet<>();

//    @JsonIgnore
//    @Builder.Default
//    @OneToMany(mappedBy = "empleado", cascade = { CascadeType.ALL }, orphanRemoval = true)
//    private Set<ProgramacionDetalle> programacionDetalles = new HashSet<>();

}
