package com.gafahtec.consultorio.service.impl;

import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.HistoriaClinicaRequest;
import com.gafahtec.consultorio.dto.response.ClienteResponse;
import com.gafahtec.consultorio.dto.response.HistoriaClinicaResponse;
import com.gafahtec.consultorio.model.consultorio.Cliente;
import com.gafahtec.consultorio.model.consultorio.HistoriaClinica;
import com.gafahtec.consultorio.repository.IClienteRepository;
import com.gafahtec.consultorio.repository.IHistoriaClinicaRepository;
import com.gafahtec.consultorio.service.IHistoriaClinicaService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class HistoriaClinicaServiceImpl   implements IHistoriaClinicaService {

	
	private IHistoriaClinicaRepository iHistoriaClinicaRepository;

	private IClienteRepository iClienteRepository;
	
	@Override
	public HistoriaClinicaResponse registrar(HistoriaClinicaRequest request) {
		var historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties( request, historiaClinica);
		var cliente = new Cliente();
		BeanUtils.copyProperties( request, cliente);
		
		historiaClinica.setCliente(cliente);
		log.info(historiaClinica);
		log.info(cliente);
		var obj = iHistoriaClinicaRepository.save(historiaClinica);
		
		return entityToResponse(obj);
	}

	@Override
	public HistoriaClinicaResponse modificar(HistoriaClinicaRequest request) {
		var historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties( request, historiaClinica);
		var cliente = new Cliente();
		BeanUtils.copyProperties( request, cliente);
		var clienteUpdate = iClienteRepository.save(cliente);
		historiaClinica.setCliente(clienteUpdate);
		var obj = iHistoriaClinicaRepository.save(historiaClinica);
		return entityToResponse(obj);
	}

	@Override
	public Set<HistoriaClinicaResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoriaClinicaResponse listarPorId(Integer id) {
		return entityToResponse(iHistoriaClinicaRepository.findById(id).get());
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}
	private HistoriaClinicaResponse entityToResponse(HistoriaClinica entity) {
		var response = new HistoriaClinicaResponse();
		BeanUtils.copyProperties(entity, response);
		
		var clienteResponse = new ClienteResponse();
		BeanUtils.copyProperties(entity.getCliente(), clienteResponse);
		response.setCliente(clienteResponse);
		return response;
	}

}
