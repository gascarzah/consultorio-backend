package com.gafahtec.consultorio.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@ToString(exclude = { "usuarios", "programacionDetalles" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    @ManyToOne
    @JoinColumn(name = "numero_documento", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;
    
    @ManyToOne
    @JoinColumn(name = "id_tipoEmpleado", nullable = true)
    private TipoEmpleado tipoEmpleado;


    private Boolean estado;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "empleado", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Usuario> usuarios = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "empleado", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<ProgramacionDetalle> programacionDetalles = new ArrayList<>();

}
