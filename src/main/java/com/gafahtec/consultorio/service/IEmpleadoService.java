package com.gafahtec.consultorio.service;

import com.gafahtec.consultorio.dto.request.EmpleadoRequest;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface IEmpleadoService extends ICRUD<EmpleadoRequest, EmpleadoResponse, Integer>{

	Page<EmpleadoResponse> listarPageable(Pageable pageable);

	Set<EmpleadoResponse> listarPorRol(Integer idRol);
    
	Set<EmpleadoResponse> listarEmpleadosPorEmpresa(Integer idEmpresa);
}
