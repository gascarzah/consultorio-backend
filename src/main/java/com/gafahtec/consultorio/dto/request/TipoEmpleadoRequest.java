package com.gafahtec.consultorio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
public class TipoEmpleadoRequest {

    private Integer idTipoEmpleado;
    private String descripcion;
    private String estado;
    
}
