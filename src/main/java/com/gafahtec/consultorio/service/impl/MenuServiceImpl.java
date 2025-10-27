package com.gafahtec.consultorio.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.MenuRequest;
import com.gafahtec.consultorio.dto.response.MenuResponse;
import com.gafahtec.consultorio.model.auth.Menu;
import com.gafahtec.consultorio.repository.IMenuRepository;
import com.gafahtec.consultorio.service.IMenuService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class MenuServiceImpl implements IMenuService {

	private IMenuRepository iMenuRepository;

	@Override
	public Page<MenuResponse> listarPageable(Pageable pageable) {
		return iMenuRepository.findAll(pageable)
				.map(this::entityToResponse);
	}

	@Override
	public Page<MenuResponse> buscarMenus(String search, Pageable pageable) {
		System.out.println("=== BUSCAR MENÚS DEBUG ===");
		System.out.println("Search term: '" + search + "'");

		Page<Menu> menus = iMenuRepository.buscarMenus(search, pageable);
		System.out.println("Total menús found: " + menus.getTotalElements());

		return menus.map(this::entityToResponse);
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
	public List<MenuResponse> listar() {

		return iMenuRepository.findAll().stream().map(this::entityToResponse).collect(Collectors.toList());
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
