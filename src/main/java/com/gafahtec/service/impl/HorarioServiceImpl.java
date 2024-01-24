package com.gafahtec.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.dto.request.HorarioRequest;
import com.gafahtec.dto.response.HorarioResponse;
import com.gafahtec.model.consultorio.Horario;
import com.gafahtec.repository.IHorarioRepository;
import com.gafahtec.service.IHorarioService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class HorarioServiceImpl   implements IHorarioService {

	
	private IHorarioRepository iHorarioRepository;
	
	

    @Override
    public Page<HorarioResponse> listarPageable(Pageable pageable) {
        return iHorarioRepository.findAll(pageable)
        		.map(this::entityToResponse);
    }



	@Override
	public HorarioResponse registrar(HorarioRequest request) {
		var horario = Horario.builder().build();
        BeanUtils.copyProperties(request, horario);
        var obj = iHorarioRepository.save(horario);

        log.info("objeto creado " + obj);
		return entityToResponse(obj);
	}



	@Override
	public HorarioResponse modificar(HorarioRequest request) {
		var horario = Horario.builder().build();
        BeanUtils.copyProperties(request, horario);
        var obj = iHorarioRepository.save(horario);

        log.info("objeto creado " + obj);
        return entityToResponse(obj);
	}



	@Override
	public Set<HorarioResponse> listar() {
		
		return iHorarioRepository.findAll().stream()
				.map(this::entityToResponse).collect(Collectors.toSet());
	}



	@Override
	public HorarioResponse listarPorId(Integer id) {
		
		return entityToResponse(iHorarioRepository.findById(id).get());
	}



	@Override
	public void eliminar(Integer id) {
		
		
	}


	private HorarioResponse entityToResponse(Horario entity) {
		var response = new HorarioResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

}
