package com.gafahtec.consultorio.model.consultorio;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    @ManyToOne(fetch = FetchType.LAZY) // Cambié EAGER a LAZY para optimizar rendimiento
    @JoinColumn(name = "numero_documento", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_cliente"))
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY) // Cambié EAGER a LAZY para optimizar rendimiento
    @JoinColumn(name = "id_horario", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_horario"))
    private Horario horario;

    @ManyToOne(fetch = FetchType.LAZY) // Cambié EAGER a LAZY para optimizar rendimiento
    @JoinColumn(name = "id_programacion_detalle", nullable = true, foreignKey = @ForeignKey(name = "FK_cita_programaciondet"))
    private ProgramacionDetalle programacionDetalle;

    private Boolean atendido;

    private String informe;

    private Integer estado;
}
