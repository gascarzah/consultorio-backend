package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.dto.request.ClienteRequest;
import com.gafahtec.consultorio.dto.response.ClienteResponse;


public interface IClienteService extends ICRUD<ClienteRequest,ClienteResponse, String>{

	Page<ClienteResponse> listarPageable(Pageable pageable);
	
	public ClienteResponse registrarConHistoriaClinica(ClienteRequest request) ;
}
