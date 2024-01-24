package com.gafahtec.model.consultorio;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @JoinColumn(name = "numero_documento", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_cliente") )
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_horario", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_horario"))
    private Horario horario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_programacion_detalle", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_programaciondet"))
    private ProgramacionDetalle programacionDetalle;
    
    @Column(columnDefinition="TEXT") 
	private String informe;
    
    private Boolean atendido;
    
    private Integer estado;

	
}
