package com.gafahtec.consultorio.dto.response;

import java.io.Serializable;

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
public class HorarioResponse implements Serializable{
	  private Integer idHorario;

	    private String descripcion;
	    
	    private Integer idEmpresa;

  
    
   
}
