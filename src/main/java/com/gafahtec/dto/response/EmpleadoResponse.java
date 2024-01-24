package com.gafahtec.dto.response;

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
public class EmpleadoResponse implements Serializable{
	private String numeroDocumento;
	
    private Integer idEmpresa;


    private PersonaResponse persona;


    private EmpresaResponse empresa;
	

    private Boolean activo;
}
