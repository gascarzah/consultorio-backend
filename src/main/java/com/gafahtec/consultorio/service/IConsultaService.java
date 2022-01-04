package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Consulta;


public interface IConsultaService extends ICRUD<Consulta,Integer>{

	Page<Consulta> listarPageable(Pageable pageable);
}
