package com.gafahtec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.ClienteRequest;
import com.gafahtec.dto.response.ClienteResponse;


public interface IClienteService extends ICRUD<ClienteRequest,ClienteResponse, String>{

	Page<ClienteResponse> listarPageable(Pageable pageable);
	
	public ClienteResponse registrarConHistoriaClinica(ClienteRequest request) ;
}
