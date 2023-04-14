package com.gafahtec.consultorio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString

public class CitaRequest {

    private Integer idProgramacionDetalle;
    private Integer idCliente;
    private Integer idCita;
    private Integer idHorario;
    private String informe;
    private Integer atendido;
}
