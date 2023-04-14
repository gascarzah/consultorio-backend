package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Empresa;

public interface IEmpresaService extends ICRUD<Empresa, Integer>{
    Page<Empresa> listarPageable(Pageable pageable);
}
