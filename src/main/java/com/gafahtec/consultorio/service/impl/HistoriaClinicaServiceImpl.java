package com.gafahtec.consultorio.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.gafahtec.consultorio.dto.response.EmpresaResponse;
import com.gafahtec.consultorio.mapper.HistoriaClinicaMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.HistoriaClinicaRequest;
import com.gafahtec.consultorio.dto.response.HistoriaClinicaResponse;
import com.gafahtec.consultorio.model.consultorio.HistoriaClinica;
import com.gafahtec.consultorio.repository.IHistoriaClinicaRepository;
import com.gafahtec.consultorio.service.IHistoriaClinicaService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class HistoriaClinicaServiceImpl implements IHistoriaClinicaService {

	private IHistoriaClinicaRepository iHistoriaClinicaRepository;

	@Override
	public HistoriaClinicaResponse registrar(HistoriaClinicaRequest request) {
		var historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties(request, historiaClinica);

		log.info(historiaClinica);

		var obj = iHistoriaClinicaRepository.save(historiaClinica);

		return entityToResponse(obj);
	}

	@Override
	public HistoriaClinicaResponse modificar(HistoriaClinicaRequest request) {
		var historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties(request, historiaClinica);
		var obj = iHistoriaClinicaRepository.save(historiaClinica);
		return entityToResponse(obj);
	}

	@Override
	public List<HistoriaClinicaResponse> listar() {

		return iHistoriaClinicaRepository.findAll().stream().map(this::entityToResponse).collect(Collectors.toList());
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
		log.info("entityToResponse " + entity);
		return HistoriaClinicaMapper.INSTANCE.historiaClinicaEntityToDto(entity);
	}

	public Page<HistoriaClinicaResponse> listarPageable(Pageable pageable) {

		return iHistoriaClinicaRepository.findAll(pageable)
				.map(this::entityToResponse);
	}

	@Override
	public Page<HistoriaClinicaResponse> buscarHistoriasClinicas(String search, Pageable pageable) {
		System.out.println("=== BUSCAR HISTORIAS CLÍNICAS DEBUG ===");
		System.out.println("Search term: '" + search + "'");

		Page<HistoriaClinica> historias = iHistoriaClinicaRepository.buscarHistoriasClinicas(search, pageable);
		System.out.println("Total historias clínicas found: " + historias.getTotalElements());

		return historias.map(this::entityToResponse);
	}
}
