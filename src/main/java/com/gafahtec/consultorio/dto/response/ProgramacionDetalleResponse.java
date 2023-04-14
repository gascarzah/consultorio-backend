package com.gafahtec.consultorio.dto.response;

import java.io.Serializable;
import java.util.List;

import com.gafahtec.consultorio.model.Empleado;
import com.gafahtec.consultorio.model.ProgramacionDetalle;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramacionDetalleResponse implements Serializable {

    private Empleado empleado;
    
    private List<ProgramacionDetalle> programacionDetalles;
    
    private  List< Boolean> listaDias;
    
}
