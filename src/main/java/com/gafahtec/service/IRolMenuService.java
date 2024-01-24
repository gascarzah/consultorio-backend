package com.gafahtec.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.RolMenuRequest;
import com.gafahtec.dto.response.RolMenuResponse;
import com.gafahtec.model.auth.RolMenuPK;

public interface IRolMenuService extends ICRUD<RolMenuRequest, RolMenuResponse , RolMenuPK>{


	Page<RolMenuResponse> listarPageable(Integer idRol, Pageable paging);

	Set<RolMenuResponse> listarPorId(Integer id);

//    Page<RolResponse> listarPageable(Pageable pageable);

}
