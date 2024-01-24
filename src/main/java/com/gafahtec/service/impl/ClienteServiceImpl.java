package com.gafahtec.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.dto.request.ClienteRequest;
import com.gafahtec.dto.response.ClienteResponse;
import com.gafahtec.model.consultorio.Cliente;
import com.gafahtec.model.consultorio.HistoriaClinica;
import com.gafahtec.repository.IClienteRepository;
import com.gafahtec.repository.IHistoriaClinicaRepository;
import com.gafahtec.service.IClienteService;
import com.gafahtec.util.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class ClienteServiceImpl   implements IClienteService {

	
	private IClienteRepository iClienteRepository;
	
	private IHistoriaClinicaRepository iHistoriaClinicaRepository;
	
	@Override
	public Page<ClienteResponse> listarPageable(Pageable pageable) {
		return iClienteRepository.findAll(pageable)
				.map(this::entityToResponse);

	}



	@Override
	public ClienteResponse registrar(ClienteRequest request) {
		var cliente = new Cliente();
		BeanUtils.copyProperties(request,cliente );
		var objCliente = iClienteRepository.save(cliente);
//		iHistoriaClinicaRepository.save(HistoriaClinica.builder()
//		                                                 .cliente(objCliente)
//		                                                 .build());
		return entityToResponse(objCliente);
	}

	public ClienteResponse registrarConHistoriaClinica(ClienteRequest request) {
		var historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties( request, historiaClinica);
		var cliente = new Cliente();
		BeanUtils.copyProperties( request, cliente);
		
		historiaClinica.setCliente(cliente);
		historiaClinica.setNumeroHistoriaClinica(Utils.getCurrentYear()+request.getNumeroDocumento());
//		var objCliente = iClienteRepository.save(cliente);
//		var objHistoriaClinica = HistoriaClinica.builder()
//				.numeroHistoriaClinica(Utils.getCurrentYear()+request.getNumeroDocumento())
//                .cliente(objCliente)
//                .build();
		iHistoriaClinicaRepository.save(historiaClinica);
		return entityToResponse(cliente);
	}


	@Override
	public ClienteResponse modificar(ClienteRequest request) {
		var cliente = new Cliente();
		BeanUtils.copyProperties(request,cliente );
		var obj = iClienteRepository.save(cliente);
		log.info("objeto modificado "+ obj);
		return entityToResponse(obj);
	}



	@Override
	public Set<ClienteResponse> listar() {
		// TODO Auto-generated method stub
		return iClienteRepository.findAll().stream().map(this::entityToResponse).collect(Collectors.toSet());
	}



	@Override
	public ClienteResponse listarPorId(String numeroDocumento) {
		return entityToResponse(iClienteRepository.findById(numeroDocumento).get());
	}



	@Override
	public void eliminar(String numeroDocumento) {
//		var optional = iClienteRepository.findById(numeroDocumento);
//		
//		if(optional.isPresent()) {
//			var cliente = optional.get();
//			cliente.set
//			iClienteRepository.save(null);
//		}
//		
	}
	
	private ClienteResponse entityToResponse(Cliente entity) {
		System.out.println(entity);
		var response = new ClienteResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}
}
