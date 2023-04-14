package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.TipoEmpleado;

public interface ITipoEmpleadoService extends ICRUD<TipoEmpleado, Integer>{
    Page<TipoEmpleado> listarPageable(Pageable pageable);
}
