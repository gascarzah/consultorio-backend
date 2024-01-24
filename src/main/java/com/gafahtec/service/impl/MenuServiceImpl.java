package com.gafahtec.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.dto.request.MenuRequest;
import com.gafahtec.dto.response.MenuResponse;
import com.gafahtec.model.auth.Menu;
import com.gafahtec.repository.IMenuRepository;
import com.gafahtec.service.IMenuService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class MenuServiceImpl   implements IMenuService {

	
	private IMenuRepository iMenuRepository;
	


    @Override
    public Page<MenuResponse> listarPageable(Pageable pageable) {
        return iMenuRepository.findAll(pageable)
        		.map(this::entityToResponse);
    }



	@Override
	public MenuResponse registrar(MenuRequest request) {
		var menu = Menu.builder().build();
        BeanUtils.copyProperties(request, menu);
        var obj = iMenuRepository.save(menu);

        log.info("objeto creado " + obj);
		return entityToResponse(obj);
	}



	@Override
	public MenuResponse modificar(MenuRequest request) {
		 var menu = Menu.builder().build();
         BeanUtils.copyProperties(request, menu);
        var obj = iMenuRepository.save(menu);
        return entityToResponse(obj);
	}



	@Override
	public Set<MenuResponse> listar() {
		// TODO Auto-generated method stub
		return iMenuRepository.findAll().stream().map(this::entityToResponse).collect(Collectors.toSet());
	}



	@Override
	public MenuResponse listarPorId(Integer id) {
		return entityToResponse(iMenuRepository.findById(id).get());
	}



	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	private MenuResponse entityToResponse(Menu entity) {
		var response = new MenuResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}


}
