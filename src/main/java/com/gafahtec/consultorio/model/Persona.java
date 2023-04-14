package com.gafahtec.consultorio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
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
@ToString
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Persona {

    @Id
    @NotEmpty(message = "Numero de documento no puede estar vacio")
    @Column(unique = true)
    private String numeroDocumento;
    private String tipoDocumento;
    @NotEmpty(message = "Campo nombres no puede estar vacio")
    private String nombres;
    @NotEmpty(message = "Campo apellido paterno no puede estar vacio")
    private String apellidoPaterno;
    @NotEmpty(message = "Campo apellido materno no puede estar vacio")
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
    private List<Empleado> empleados = new ArrayList<>();

}
