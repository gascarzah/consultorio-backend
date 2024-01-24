package com.gafahtec.service.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.dto.request.RolMenuRequest;
import com.gafahtec.dto.response.RolMenuResponse;
import com.gafahtec.model.auth.Menu;
import com.gafahtec.model.auth.Rol;
import com.gafahtec.model.auth.RolMenu;
import com.gafahtec.model.auth.RolMenuPK;
import com.gafahtec.repository.IRolMenuRepository;
import com.gafahtec.service.IRolMenuService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class RolMenuServiceImpl  implements IRolMenuService {

	
	private IRolMenuRepository iRolMenuRepository;
	

//    @Override
//    public Page<RolResponse> listarPageable(Pageable pageable) {
//        // TODO Auto-generated method stub
//        return iRolRepository.findAll(pageable).map(this::entityToResponse);
//    }


	@Override
	public RolMenuResponse registrar(RolMenuRequest request) {
		var entity = new RolMenu();
		var list = Arrays.asList( request.getIdsMenu());
		list.forEach(idMenu -> {
			iRolMenuRepository.save(RolMenu.builder()
					.rol(Rol.builder()
							.idRol(request.getIdRol())
							.build())
					.menu(Menu.builder()
							.idMenu(idMenu)
							.build())
					.build());
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
	public Set<RolMenuResponse> listar() {
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
	public Set<RolMenuResponse> listarPorId(Integer id) {
		 return iRolMenuRepository.findByRolOrder(id).stream().map(this::entityToResponse).collect(Collectors.toSet());
	}

}
