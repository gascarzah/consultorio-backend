package com.gafahtec.model.consultorio;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@EqualsAndHashCode(exclude = { "citas" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProgramacionDetalle  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProgramacionDetalle;

    private LocalDate fecha;

    private String diaSemana;
    private Integer numeroDiaSemana;
    private Boolean activo;

    private String numeroDocumento;
    private Integer idEmpresa;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_programacion", nullable = false, foreignKey = @ForeignKey(name = "FK_programacion"))
    private Programacion programacion;


    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "programacionDetalle", cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Cita> citas = new HashSet<>();

}
