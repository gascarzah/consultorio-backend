package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.dto.request.RolMenuRequest;
import com.gafahtec.consultorio.dto.response.MenusPorRolResponse;
import com.gafahtec.consultorio.dto.response.RolMenuResponse;
import com.gafahtec.consultorio.model.auth.Menu;
import com.gafahtec.consultorio.model.auth.Rol;
import com.gafahtec.consultorio.model.auth.RolMenu;
import com.gafahtec.consultorio.model.auth.RolMenuPK;
import com.gafahtec.consultorio.repository.IMenuRepository;
import com.gafahtec.consultorio.repository.IRolMenuRepository;
import com.gafahtec.consultorio.service.IRolMenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class RolMenuServiceImpl  implements IRolMenuService {

	
	private IRolMenuRepository iRolMenuRepository;
	private IMenuRepository iMenuRepository;

//    @Override
//    public Page<RolResponse> listarPageable(Pageable pageable) {
//        // TODO Auto-generated method stub
//        return iRolRepository.findAll(pageable).map(this::entityToResponse);
//    }


	@Override
	public RolMenuResponse registrar(RolMenuRequest request) {
		List<RolMenu> listaActual = iRolMenuRepository.findByRolOrder(request.getIdRol());
		List<Integer> nuevosIdsMenu   = Arrays.asList( request.getIdsMenu());


		List<Integer> idsMenusActuales = listaActual.stream()
				.map(rolMenu -> rolMenu.getMenu().getIdMenu())
				.collect(Collectors.toList());


		// **1. Eliminar los menús que ya no están en la nueva lista**
		listaActual.forEach(rolMenu -> {
			if (!nuevosIdsMenu.contains(rolMenu.getMenu().getIdMenu())) {
				iRolMenuRepository.delete(rolMenu);
			}
		});

		// **2. Insertar solo los nuevos menús que no existen**
		nuevosIdsMenu.forEach(idMenu -> {
			if (!idsMenusActuales.contains(idMenu)) {
				iRolMenuRepository.save(RolMenu.builder()
						.rol(Rol.builder().idRol(request.getIdRol()).build())
						.menu(Menu.builder().idMenu(idMenu).build())
						.build());
			}
		});
//		var obj = iRolMenuRepository.save(entity);
		return null;
	}


	
	private RolMenuResponse entityToResponse(RolMenu entity) {
		var response = new RolMenuResponse();
		var rol = new Rol();
		BeanUtils.copyProperties(entity.getRol(),rol );
		var menu = new Menu();
		BeanUtils.copyProperties(entity.getMenu(),menu );
		response.setMenu(menu);
		response.setRol(rol);
		return response;
	}



	@Override
	public RolMenuResponse modificar(RolMenuRequest request) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<RolMenuResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public RolMenuResponse listarPorId(RolMenuPK id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void eliminar(RolMenuPK id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Page<RolMenuResponse> listarPageable(Integer idRol, Pageable pageable) {
		
		 return iRolMenuRepository.listarPageable(idRol,pageable).map(this::entityToResponse);
	}



	@Override
	public List<MenusPorRolResponse> listarPorId(Integer id) {
		List<Menu> menus = iMenuRepository.findAll();
		List<RolMenu> lista = iRolMenuRepository.findByRolOrder(id);

		List<MenusPorRolResponse> filteredMenus = menus.stream()
				.map(menu -> {

					RolMenu rolMenu = lista.stream()
							.filter(rm -> rm.getMenu().getIdMenu().equals(menu.getIdMenu()))
							.findFirst()
							.orElse(null);


					return MenusPorRolResponse.builder()
							.idMenu(menu.getIdMenu())
							.nombre(menu.getNombre())
							.activo(rolMenu != null ? true : false)
							.build();
				})
				.collect(Collectors.toList());

		// Loguea el resultado para comprobar que todo está funcionando correctamente
		log.info("lista ===> " + filteredMenus);

		return filteredMenus;
	}

	public List<RolMenuResponse> listarRolPorMenus(Integer id) {

		return iRolMenuRepository.findByRolMenu(id).stream().map(this::entityToResponse).collect(Collectors.toList());


	}
}
