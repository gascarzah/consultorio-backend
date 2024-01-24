package com.gafahtec.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.EmpleadoRequest;
import com.gafahtec.dto.response.EmpleadoResponse;
import com.gafahtec.model.auth.EmpleadoId;


public interface IEmpleadoService extends ICRUD<EmpleadoRequest, EmpleadoResponse, EmpleadoId>{

	Page<EmpleadoResponse> listarPageable(Pageable pageable);

	Set<EmpleadoResponse> listarPorRol(Integer idRol);
    
	Set<EmpleadoResponse> listarEmpleadosPorEmpresa(Integer idEmpresa);
}
