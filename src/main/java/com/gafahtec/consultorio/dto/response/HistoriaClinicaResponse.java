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
public class HistoriaClinicaResponse implements Serializable{
	private String idHistoriaClinica;
	
	private String ectoscopia;
	private String alergia;
	private String motivo;
	private String antecedentesMedicos;

	
	private ClienteResponse cliente;
}
