package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Rol;

public interface IRolService extends ICRUD<Rol, Integer>{

    Page<Rol> listarPageable(Pageable pageable);

}
