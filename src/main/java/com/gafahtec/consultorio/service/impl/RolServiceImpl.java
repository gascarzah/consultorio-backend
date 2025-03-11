package com.gafahtec.consultorio.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.RolRequest;
import com.gafahtec.consultorio.dto.response.RolResponse;
import com.gafahtec.consultorio.model.auth.Rol;
import com.gafahtec.consultorio.repository.IRolRepository;
import com.gafahtec.consultorio.service.IRolService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class RolServiceImpl  implements IRolService {

	
	private IRolRepository iRolRepository;
	

    @Override
    public Page<RolResponse> listarPageable(Pageable pageable) {
        // TODO Auto-generated method stub
        return iRolRepository.findAll(pageable).map(this::entityToResponse);
    }


	@Override
	public RolResponse registrar(RolRequest request) {
		var entity = new Rol();
		BeanUtils.copyProperties(request, entity);
		var obj = iRolRepository.save(entity);
		return entityToResponse(obj);
	}


	@Override
	public RolResponse modificar(RolRequest request) {
		var entity = new Rol();
		BeanUtils.copyProperties(request, entity);
		var obj = iRolRepository.save(entity);
		return entityToResponse(obj);
	}


	@Override
	public Set<RolResponse> listar() {
		return iRolRepository.findAll()
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
	}


	@Override
	public RolResponse listarPorId(Integer id) {
		return entityToResponse(iRolRepository.findById(id).get());
	}


	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	private RolResponse entityToResponse(Rol entity) {
		var response = new RolResponse();
		BeanUtils.copyProperties(entity,response );
		return response;
	}

}
