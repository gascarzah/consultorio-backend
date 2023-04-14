package com.gafahtec.consultorio.dto.request;

import com.gafahtec.consultorio.model.Cita;

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
public class HistoriaClinicaDetalleRequest {


    private Cita cita;
    private String informe;
    private Integer atendido;
    
   
}
