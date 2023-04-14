package com.gafahtec.consultorio.service;

import com.gafahtec.consultorio.model.Empleado;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEmpleadoService extends ICRUD<Empleado,Integer>{

	Page<Empleado> listarPageable(Pageable pageable);

    List<Empleado> listarPorRol(Integer idRol);
    
    List<Empleado> listarPorTipoEmpleado(Integer idEmpresa, String descTipoEmpleado);
}
