package com.gafahtec.consultorio.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.EmpresaRequest;
import com.gafahtec.consultorio.dto.response.EmpresaResponse;
import com.gafahtec.consultorio.dto.response.RolResponse;
import com.gafahtec.consultorio.model.auth.Empresa;
import com.gafahtec.consultorio.repository.IEmpresaRepository;
import com.gafahtec.consultorio.service.IEmpresaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class EmpresaServiceImpl implements IEmpresaService {

	private IEmpresaRepository iEmpresaRepository;

	@Override
	public Page<EmpresaResponse> listarPageable(Pageable pageable) {
		// TODO Auto-generated method stub
		return iEmpresaRepository.findAll(pageable)
				.map(this::entityToResponse);
	}

	@Override
	public Page<EmpresaResponse> buscarEmpresas(String search, Pageable pageable) {
		System.out.println("=== BUSCAR EMPRESAS DEBUG ===");
		System.out.println("Search term: '" + search + "'");

		Page<Empresa> empresas = iEmpresaRepository.buscarEmpresas(search, pageable);
		System.out.println("Total empresas found: " + empresas.getTotalElements());

		return empresas.map(this::entityToResponse);
	}

	private EmpresaResponse entityToResponse(Empresa entity) {
		var response = new EmpresaResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	@Override
	public EmpresaResponse registrar(EmpresaRequest request) {
		var entity = new Empresa();
		BeanUtils.copyProperties(request, entity);
		var obj = iEmpresaRepository.save(entity);
		return entityToResponse(obj);
	}

	@Override
	public EmpresaResponse modificar(EmpresaRequest request) {
		var entity = new Empresa();
		BeanUtils.copyProperties(request, entity);
		var obj = iEmpresaRepository.save(entity);
		return entityToResponse(obj);
	}

	@Override
	public List<EmpresaResponse> listar() {
		return iEmpresaRepository.findAll()
				.stream().map(this::entityToResponse).collect(Collectors.toList());
	}

	@Override
	public EmpresaResponse listarPorId(Integer id) {
		return entityToResponse(iEmpresaRepository.findById(id).get());
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub

	}
}
