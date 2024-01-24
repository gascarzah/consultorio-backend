package com.gafahtec.dto.request;

import com.gafahtec.dto.response.ClienteResponse;

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
public class HistoriaClinicaRequest {

    private String idHistoriaClinica;

    private String ectoscopia;
    private String alergia;
    private String motivo;
    private String antecedentesMedicos;
    private Integer idCliente;
    

	private String nombres;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;

	private String tipoDocumento;
	
	private String numeroDocumento;

	private String direccion;

	private String telefono;
	private String celular;
	
	private String email;
}
