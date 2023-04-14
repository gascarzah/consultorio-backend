package com.gafahtec.consultorio.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cita  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_cliente") )
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_horario", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_horario"))
    private Horario horario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_programacion_detalle", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_programaciondet"))
    private ProgramacionDetalle programacionDetalle;
    
    private Integer atendido;
    
    private String informe;
}
