package com.gafahtec.model.auth;

import java.util.ArrayList;
import java.util.List;

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
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TipoEmpleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoEmpleado;
    
    private String descripcion;
    
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "tipoEmpleado", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Empleado> empleados = new ArrayList<>();

}
