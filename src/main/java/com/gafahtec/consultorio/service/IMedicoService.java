package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Medico;


public interface IMedicoService extends ICRUD<Medico,Integer>{

	Page<Medico> listarPageable(Pageable pageable);
}
